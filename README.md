# ChatSystem

## COMPILATION 

sur un terminal : 
- mvn compile
- mvn package
  
  On utilise la commande mvn compile afin de compiler toutes les classes du projet.
  On utilise la commande mvn package afin de compiler toutes les classes et produire un unique fichier JAR (Java Archive) dans le répertoire cible (target).

## EXECUTION SUR TERMINAL

On ouvre deux terminaux à partir du répertoire où se situe notre projet :

sur terminal n°1 : 
- mvn package
- mvn exec:java

sur terminal n°2 :
- ssh insa-20659 (on se connecte sur une autre machine du réseau: exemple d'une machine)
- cd /home/... (répertoire dans lequel se situe le projet)
- mvn exec:java

Sur le terminal n°1, on produit un fichier JAR puis on exécute le programme grâce à la commande java, en spécifiant le chemin (classpath) défini sur le répertoire target.
Ensuite, sur le terminal n°2, on utilise la commande ssh pour accéder à un système distant (ici, on a pris l'exemple de la machine insa-20659).
Ensuite, avec la commande cd on retourne au répertoire où se situe notre projet. 
Une fenétre d'accueil apparaît, on appuie sur le bouton "Start" pour commencer. On peut maintenant entrer le nickname choisi dans la frame pour commencer.
Après, on exécute le programme (à l'aide de la même commande qu'on a utilisé sur le terminal n°1) sur le terminal n°2.
Sur le terminal n°2, on fait la même chose que précedemment. Si le nickname est déjà pris, on écrit un nouveau nickname (un message s'affiche pour nous indiquer qu'il faut en choisir un autre).
On a ensuite une fenêtre avec la liste de contacts qui apparaît: il faut appuyer sur "Accept" pour commencer une session de clavardage avec quelqu'un, sur "Update" pour mettre à jour la liste de contacts et voir les personnes qui se sont connectées après nous, et sur "Change Nickname" pour changer de pseudo pendant l'utilisation de la session.
Si on a ouvert une session de clavardage entre deux personnes, l'historique des messages horo-datés est affiché, et il est possible d'échanger entre les deux machines.
Si on envoie un message à une personne qui n'a pas ouvert la frame de chat, le message apparaîtra dans l'historique lorsque la session sera ouverte.

## TESTS

Tests unitaires des fonctions non liées au réseau sur IDE.
Tests unitaires des fonctions liées à la base de données.
Tests automatisés sur GitHub.



