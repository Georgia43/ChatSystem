## tech stack  

# Découverte des utilisateurs connectés 

On a utilisé un protocole UDP car il permet de diffuser des messages à plusieurs destinataires simultanément. TCP n’a pas cette fonctionnalité car c’est un protocole point à point (l’envoie de message se fait à un destinataire avec lequel la connexion doit être établie au préalable). De plus, on ne voulait pas garantir l’ordre de livraison des données.
Cependant, le protocole UDP a certaines limites : il ne permet pas la retransmission de données en cas de perte (ne garantit ni ordre ni fiabilité). Par ailleurs, il n’a pas de mécanisme de sécurité assurant la protection de la découverte de contacts (par exemple contre des accès non autorisés ou attaques).  

# Échange de messages
 
On a utilisé un protocole TCP. En effet, pour l’échange de messages, chaque discussion est entre deux utilisateurs (on ne fait pas de broadcast dans cette partie). Ainsi, on voulait garantir une communication fiable et bidirectionnelle entre deux points. TCP assure la livraison ordonnée de messages et sans pertes de données. 
Même si ce protocole est plus adapté pour l’échange de messages dans le chatsystem, TCP peut avoir plus de surcharge, en particulier dans des scénarios avec un grand nombre de connexions de courte durée car il maintient des informations d'état pour chaque connexion, et cet état doit être géré à la fois côté serveur et côté client. 
Au niveau de la base de données, nous avons choisi SQLite car sa configuration est relativement simple (création d’un URL pour l’utiliser) et il ne nécessite pas de serveur distinct. De plus, le stockage des données est local, car elles sont juste stockées dans un fichier créé automatiquement grâce à l’URL.
Enfin, concernant l’interface utilisateur, nous avons utilisé Java Swing car nous avons voulu suivre le modèle MVC pour plus d'organisation et car nous l’avions déjà utilisé en PDLA donc nous avions une idée de son fonctionnement. Grâce à cette dernière, nous avons principalement créé une interface utilisateur de connection demandant seulement le pseudo (ayant estimé qu’autre chose ne serait pas nécessaire), une interface présentant la liste des utilisateurs connectés avec lesquels il est possible d’échanger, et une interface de conversation par utilisateur, les trois fonctionnalités principales de l’application.


# Testing policy

Pour nos tests, nous avons créé des tests unitaires en utilisant des assertions et la méthode suite() qui fournit une suite de tests à exécuter et renvoie une instance de TestSuite. Nos classes héritent de TestCase et utilisent ses méthodes. 
Pour la découverte de contacts, nous avons créé une classe dans laquelle on a testé toutes les fonctionnalités concernant la gestion de la réception et de la liste de contact (par exemple, la réception d’un nickname déjà pris, l’ajout d’un utilisateur dans la liste de contact, etc.). 
Pour la Database, nous avons commencé par tester la connexion ainsi que la création de tables. Ensuite, nous avons créé tous les tests concernant la gestion des messages (leur présence dans la database, la réception, etc.). Nous nous sommes aussi concentrés sur toutes les fonctionnalités de la database concernant les utilisateurs. Ainsi, nous avons par exemple l’ajout d’un utilisateur dans la database, le changement de son nickname, etc. 
Nous n’avons pas testé tout ce qui correspond à la partie réseau (broadcast, client/serveur etc…), car il n’est pas possible de tester ces fonctionnalités seulement grâce à des tests unitaires.

# Highlights

Envoi de messages sur le bon socket -> classe ClientsList List ( et ClientHandler dans Server)
Gestion de la réception de messages avec Observer -> classe ClientHandler
Gestion des erreurs -> classe Broadcast : logger
Gestion de la réception -> classe HandleReceived
