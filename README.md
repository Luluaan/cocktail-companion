# Cocktail Companion
- Bozier Luan
- Vivone Tom
- Villarejo Maxime

## Guide d’installation (Windows)

### 1. Cloner le projet
```bash
git clone https://github.com/Luluaan/cocktail-companion.git
```

### 2. Lancer les containers (base de données et Apache ActiveMQ)
```bash
docker-compose up --build -d
```

### 3. Démarrer les services

#### Cocktail Service
```bash
cd cocktail-service
mvnw install
mvnw spring-boot:run
```

#### Feedback Service
```bash
cd feedback-service
mvnw install
mvnw spring-boot:run
```

---

## Exemple d’utilisation

### 1. Trouver un cocktail par son nom (ex: "margarita")
![Recherche cocktail](documentation/1.png)

### 2. Ajouter une note et un commentaire au cocktail (ex: idDrink = 11007)
![Ajout feedback](documentation/2.png)

### 3. Visualiser à nouveau le cocktail avec feedback(s) et note moyenne
![Voir feedback](documentation/3.png)

## Dépendances spécifiques
Voici la liste des packages spécifiques qui sortent du cadre du cours que nous avons utilisés:
1. Validation
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```
Permet d'ajouter des annotations sur des attributs de classe permettant de valider leur type, s'assurer qu'ils soient non null etc...

Nous l'avons utilisé dans la classe JmsMessage du cocktail service pour s'assurer d'envoyer des messages corrects dans notre queue.
2. Lombok
```
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```
Permet d'ajouter des annotations de classe permettant d'ajouter automatiquement des getters, setters, etc aux attributs.