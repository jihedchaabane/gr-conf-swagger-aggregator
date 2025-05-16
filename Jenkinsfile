pipeline {
    agent any
    tools {
        maven 'MAVEN'
    }
    environment {
		// Chemin du module Spring Boot
        MODULE_PATH = 'gr-conf-swagger-aggregator'
        // Nom de l'image Docker
        DOCKER_IMAGE = 'jihed123/gr-conf-swagger-aggregator:0.0.1-SNAPSHOT'
        // Nom du conteneur
        CONTAINER_NAME = 'container-gr-conf-swagger-aggregator'
        // Port de l'application
        APP_PORT = '8765'
        // common-network
        DOCKER_NETWORK = 'springboot-network'
        
        CONFIG_SERVER_ADDR = 'http://container-springboot-conf-config-server:8762'
    }
    stages {
        stage('Checkout') {
            steps {
                // Récupérer le code depuis le dépôt Git
                checkout scm
            }
        }
        stage('Build Module') {
            steps {
                script {
                    // Construire uniquement le module Spring Boot
                    sh """
                        mvn clean package
                    """
                }
            }
        }
        
//        stage('SonarQube Analysis') {
//            steps {
//                // Nom du serveur configuré dans l'étape 2
//                withSonarQubeEnv('SonarQubeServer') {
//                    sh '''
//                        mvn sonar:sonar \
//                            -Dsonar.java.binaries=. \
//                            -Dsonar.projectName=${MODULE_PATH} \
//                            -Dsonar.projectKey=${MODULE_PATH}
//                    '''
//                }
//            }
//        }
        
        stage('Stop and Remove Existing Container') {
            steps {
                script {
                    // Arréter et supprimer le conteneur s'il existe
                    sh """
                        docker stop ${CONTAINER_NAME} || true
                        docker rm ${CONTAINER_NAME} || true
                    """
                }
            }
        }
        
        stage('Remove Docker Image') {
            steps {
                script {
                    // Define the image name and tag
                    // def imageName = "my-app"
                    // def imageTag = "latest"
                    // def fullImage = "${imageName}:${imageTag}"
                    
                    // Check if the image exists and remove it
                    
                    //sh """
                    //    if [ \$(docker images -q ${fullImage}) ]; then
                    //        echo "Removing existing Docker image ${fullImage}"
                    //        docker rmi ${fullImage}
                    //    else
                    //        echo "Docker image ${fullImage} does not exist"
                    //    fi
                    //    if [ \$(docker images -q ${fullImage}) ]; then
                    //        echo "Removing existing Docker image ${fullImage}"
                    //        docker rmi ${fullImage}
                    //    else
                    //        echo "Docker image ${fullImage} does not exist"
                    //    fi
                    //"""
                    
                    sh """
                        if [ \$(docker images -q ${DOCKER_IMAGE}) ]; then
                            echo "Removing existing Docker image ${DOCKER_IMAGE}"
                            docker rmi ${DOCKER_IMAGE}
                        else
                            echo "Docker image ${DOCKER_IMAGE} n existe pas !"
                        fi
                    """
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    sh """
                        docker build -t ${DOCKER_IMAGE} .
                    """
                }
            }
        }
		

//        stage('Push Docker Image') {
//            steps {
//                script {
//                    // Pousser l'image vers un registre (optionnel)
//                  docker.withRegistry('https://index.docker.io/v1/', 'docker-credentials-id') {
//                      sh """
//                         docker push ${DOCKER_IMAGE}
//                        """
//                    }
//                }
//            }
//        }
		
		stage('Run Docker Container') {
            steps {
                script {
                    // Lancer le conteneur
                    sh """
                        docker run \
                        	-e SPRING_PROFILES_ACTIVE=${ACTIVE_PROFILE} \
                        	-e CONFIG_SERVER_ADDR=${CONFIG_SERVER_ADDR} \
                        	-d --name ${CONTAINER_NAME} --network ${DOCKER_NETWORK} \
                        	-p ${APP_PORT}:${APP_PORT} \
                        	${DOCKER_IMAGE}
                    """
                }
            }
        }

//        stage('Verify Container') {
//            steps {
//                script {
//                    // Vérifier que le conteneur est en cours d'exécution
//                    sh """
//                        sleep 10 # Attendre que le conteneur démarre
//                        docker ps --filter "name=${CONTAINER_NAME}" --filter "status=running" | grep ${CONTAINER_NAME}
//                    """
//                    // Vérifier que l'application répond
//                    sh """
//                        curl --fail http://localhost:${APP_PORT}/actuator/health || exit 1
//                    """
//                }
//            }
//        }

//		stage('Verify Container2') {
//			steps {
//				script {
//					sh """
//						# Attendre que le conteneur soit en cours d'exécution
//						sleep 10
//						docker ps --filter "name=${CONTAINER_NAME}" --filter "status=running" | grep ${CONTAINER_NAME}
//					"""
//					sh """
//						# Tenter la connexion plusieurs fois
//						for i in {1..10}; do
//							curl -v --fail http:localhost:${APP_PORT}/actuator/health && break
//							echo "Attente de l'application... (\$i/10)"
//							sleep 5
//						done
//						# Vérification finale
//						curl --fail http://localhost:${APP_PORT}/actuator/health || exit 1
//					"""
//				}
//			}
//		}

    }
    post {
        success {
            echo 'Application SpringBoot démarrée avec succés !'
        }
        failure {
            echo 'Echec du démarrage de l\'application.'
        }
    }
}