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




                    <button type="button" class="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#logInForm">
                        LogIn
                    </button>
                    <div class="modal fade" id="logInForm" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="logInFormLabel" aria-hidden="true">
                        <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                            <h1 class="modal-title fs-5" id="logInFormLabel">Log in</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form action="" id="logIn">

                                        <div class="mb-3 row">

                                            <label for="inputUsername" class="col-md-2 col-form-label">Username</label>

                                            <div class="col-md-10">
                                                <input type="text" class="form-control" maxlength="30" id="inputUsername">
                                            </div>
                                        </div>

                                        <div class="mb-3 row">

                                            <label for="inputPassword" class="col-md-2 col-form-label">Password</label>

                                            <div class="col-md-10">
                                                <input type="password" class="form-control" maxlength="30" id="inputPassword">
                                            </div>
                                        </div>


                                </form>
                            </div>
                            <div class="modal-footer">
                            <button class="btn btn-secondary" data-bs-target="#exampleModalToggle2" data-bs-toggle="modal">Register</button>
                            <button type="button" class="btn btn-primary">Log in</button>
                            </div>
                        </div>
                        </div>
                    </div>


                </div>
                <div class="modal fade" id="exampleModalToggle2" aria-hidden="true" aria-labelledby="exampleModalToggleLabel2" tabindex="-1">
                    <div class="modal-dialog ">
                        <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalToggleLabel2">Register</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">

                            <form action="" id="registerForm">

                            <div class="mb-3 row">

                                <label for="inputUsername" class="col-md-2 col-form-label">Username</label>

                                <div class="col-md-10">
                                    <input type="text" class="form-control" maxlength="30" id="inputUsername">
                                </div>
                            </div>

                            <div class="mb-3 row">

                                <label for="inputPassword" class="col-md-2 col-form-label">Password</label>

                                <div class="col-md-10">
                                    <input type="password" class="form-control" maxlength="30" id="inputPassword">
                                </div>
                            </div>

                    </form>


                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-primary" data-bs-target="#logInForm" data-bs-toggle="modal">Back to log in</button>
                            <button type="button" class="btn btn-primary">Send</button>
                        </div>
                        </div>
                    </div>
                </div>




                </div>
            </div>
        </nav>
    `)
}