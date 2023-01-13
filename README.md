TopKino
-------

1. Install [Docker](https://www.docker.com/) and docker-compose (included in Docker Desktop).
2. Start Docker
3. Go to project root
4. Run:

    ```bash
    docker-compose up --build
    ```

5. UI is accessible under <http://localhost:8081>, API - under <http://localhost:8080>

**NOTE**: The DB used is an in-memory one, pre-filled with records for demo purposes. All changes will go lost after shutdown.
