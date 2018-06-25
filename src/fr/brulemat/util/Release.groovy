package fr.brulemat.util;

class Release implements Serializable {
    def script
    String releaseVersion
    String nextVersion

    Release(script) {
        this.script = script
    }

    def maven(Maven mvn) {
        defineVersion(mvn.extractVersion())
        gitRelease()
    }

    def defineVersion(String currentVersion) {
        if (currentVersion.length() >= 9 && currentVersion.substring(currentVersion.length() - 9).equals('-SNAPSHOT')) {
            releaseVersion = currentVersion.substring(0, currentVersion.length() - 9)
        }
        String[] version = releaseVersion.tokenize(".")
        version[version.length - 2] = version[version.length - 2].toInteger() + 1
        version[version.length - 1] = 0
        nextVersion = version.join('.').concat('-SNAPSHOT')

        script.sh("echo release Version: ${releaseVersion}")
        script.sh("echo next Version: ${nextVersion}")
    }

    def gitRelease() {

    }

    def scripted() {
        script.sh '''
# pour empécher le mode intéractif de gitflow
export GIT_MERGE_AUTOEDIT=no

# existe-t-il déjà une release
if [ "$(git branch | grep staging/$version)" == "" ]; then

    git flow release start $version
    if [ $? -gt 0 ]; then
        echo "ERROR! git flow release start $version"
        echo "release --release=$version --new_version=$newVersion"
        exit -1
    fi
    git flow release publish $version
    if [ $? -gt 0 ]; then
        echo "ERROR! flow release publish $version"
        echo "release --release=$version --new_version=$newVersion"
        exit -1
    fi

    mvn versions:set -DnewVersion=$version
    find . -name 'pom.xml.versionsBackup' -delete

    mvn clean package
    if [ $? -gt 0 ]; then
        echo "ERROR!"
        echo "release --release=$version --new_version=$newVersion"
        exit -1
    fi
    mvn clean
fi

echo "création de la release et modification des poms"
git commit -a -m "Livraison : $version - création de la release et modification des poms"
git push
if [ $? -gt 0 ]; then
    echo "ERROR! push release"
    echo "release --release=$version --new_version=$newVersion"
    exit -1
fi

echo "checkout master"
git checkout master
git pull

git checkout release/$version

echo "création du tag et merge sur le master"
git flow release finish $version -m "Livraison : $version - création du tag et merge sur le master"

if [ $? -gt 0 ]; then
    echo "ERROR! git flow release finish"
    echo "release --release=$version --new_version=$newVersion"
    exit -1
fi
unset GIT_MERGE_AUTOEDIT


#mvn clean package
mvn clean deploy -P nexus
if [ $? -gt 0 ]; then
    echo "ERROR!"
    echo "release --release=$version --new_version=$newVersion"
    exit -1
fi
mvn clean

git commit -a -m "Livraison : $version - push du master"
git push
if [ $? -gt 0 ]; then
    echo "ERROR!"
    echo "release --release=$version --new_version=$newVersion"
    exit -1
fi


git commit -a -m "Livraison : $version - push du tag"
git push --tags
if [ $? -gt 0 ]; then
    echo "ERROR!"
    echo "release --release=$version --new_version=$newVersion"
    exit -1
fi

git checkout develop
mvn versions:set -DnewVersion=$newVersion
find . -name 'pom.xml.versionsBackup' -delete
git commit -a -m "Livraison : $version - Nouvelle version des poms : $newVersion"
git push
if [ $? -gt 0 ]; then
    echo "ERROR!"
    exit -1
fi
'''
    }
}