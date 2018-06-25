# Continuous Integration
Jenkins pipeline shared library for Continous Integration


For testing on local<br>
Config jenkins :   add Global Pipeline Librarie<br>
<ul>
    <li>Name : continuousIntegration</li>
    <li>Version : master</li>
    <li>Retrieval method : Modern SCM </li>
    <li>Source Code Management : 
        <ul>
            <li>for local dev : git : your repo</li>
            <li>for use : GitHub : https://github.com/Brulemat/continuousIntegration.git</li>
        </ul> 
    </li>
</ul>

Edit Jenkinsfile for local dev and add branch : @Library('continuousIntegration@develop') _ 