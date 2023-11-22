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
- java -cp target/Projet_Java-1.0_SNAPSHOT.jar ChatSystem.MainClass

sur terminal n°2 :
- ssh insa-20659 (on se connecte sur une autre machine du réseau)
- cd /home/... (répertoire dans lequel se situe le projet)
- java -cp target/Projet_Java-1.0_SNAPSHOT.jar ChatSystem.MainClass

Sur le terminal n°1, on produit un fichier JAR puis on exécute le programme grâce à la commande java, en spécifiant le chemin (classpath)
défini sur le répertoire target.
Ensuite, sur le terminal n°2, on utilise la commande ssh pour accéder à un système distant (ici, on a pris l'exemple de la machine insa-20659).
Ensuite, avec la commande cd on retourne au répertoire où se situe notre projet. 
Sur le terminal n°1, on écrit un nickname. 
Après, on exécute le programme (à l'aide de la même commande qu'on a utilisé sur le terminal n°1) sur le terminal n°2.
Sur le terminal n°2, on écrit un nickname. S'il est déjà pris, on écrit un nouveau nickname (un message s'affiche pour nous indiquer qu'il faut en choisir un autre).
À la fin, les deux listes de contacts sont affichées lorsqu'on appuie sur le bouton entrée et on attend 5 secondes.

## TESTS

Tests unitaires des fonctions non liées au réseau sur IDE,



