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