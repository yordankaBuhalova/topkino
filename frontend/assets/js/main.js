function loadNavbar() {
    $("#navigation").append(`
        <nav class="navbar navbar-expand-lg bg-body-tertiary">
            <div class="container-fluid">
                <a class="navbar-brand" href="/"><h1>TopKino</h1></a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/index.html">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/movies.html">Movies</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/rooms.html">Rooms</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">About us</a>
                    </li>
                    <li class="nav-item ">
                        <a class="nav-link" href="/users.html">Users</a>
                    </li>
                    <li class="nav-item ">
                        <a class="nav-link" href="/reservations.html">Reservations</a>
                    </li>
                    </ul>
                    <div id="hello_user"></div>
                    <button class="btn btn-outline-success d-flex" id="" type="button">Login</button>
                </div>
            </div>
        </nav>
    `)
}