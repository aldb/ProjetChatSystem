# ProjetChatSystem

Voir Rapport_POULIN_ALDEBERT.pdf

Précisions au chapitre déploiement dans le rapport :
L'adresse URL utilisée par l'application est pour un serveur Tomcat en localhost:8080 : "http://localhost:8080/WebServer/request", si autre il faut changer le champ "private final String requestUrl" dans la classe CentralizedNotifier. Dans ce cas, il faut simplement rebuild le JAR sachant que le code source est dans la version comme ProjetChatSystem_final.jar (et non en test, reference au rapport dans l'explication du déploiement).
Les fichiers JAR s'intitulent ProjetChatSystem...jar et non ProjeCtChatSystem....jar
