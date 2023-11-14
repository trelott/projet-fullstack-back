# CovidAPI

Ce projet à été généré avec SpringBoot 3.1.4

Lien pour le projet front: https://github.com/trelott/projet-fullstack-front

## Docker

Pour lancer le projet, il suffit de cloner le projet:
```git clone https://github.com/trelott/projet-fullstack-back```

Puis de lancer le docker compose:
```cd projet-fullstack-back && docker compose up -d```

## Jenkins

Pour automatiser le build avec Jenkins, il faut d'abord télécharger Jenkins: ```git clone https://github.com/jredel/jenkins-compose```

Puis lancer l'application: ```cd jenkins-compose && docker compose up```

Il faut ensuite se rendre à l'adresse http://localhost:8001 et se connecter.

Et enfin créer un nouveau pipeline avec le code contenu dans le fichier 'Jenkinsfile'

Il ne reste plus qu'à lancer le build pour build l'image docker et la push sur le hub, elle sera ensuite accessible avec 'charleswatrelot/covid-api'

