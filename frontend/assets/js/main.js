function register() {
    dataType = "json"
    var $form = $("#registerForm")

    let username = $form.find( "input[id='inputUsernameReg']" ).val()
    let password =  $form.find( "input[id='inputPasswordReg']" ).val()

    var data = {
        "username": username,
        "password": password,
    }

    $.ajax({
        url: Config().API_URL + '/users',
        type: "POST",
        data: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
        },
        error: function() {
            alert("Could not register")
        },
        success: function(data, status) {
            alert("Registered successfully")
            saveLoginDetails(username, password, false)
            localtion.reload()
        },
        dataType: dataType
    })
}

function login() {
    dataType = "json"
    var $form = $("#loginForm")

    let username = $form.find( "input[id='inputUsername']" ).val()
    let password =  $form.find( "input[id='inputPassword']" ).val()

    var data = {
        "username": username,
        "password": password,
    }

    $.ajax({
        url: Config().API_URL + '/users/authorized',
        type: "POST",
        data: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
        },
        error: function() {
            alert("Could not login")
        },
        success: function(data, status) {
            if(data.authorized) {
                saveLoginDetails(username, password, data.isAdmin)
                location.reload()
            } else {
                alert("Login incorrect")
            }
        },
        dataType: dataType
    })
}

function logout() {
    localStorage.clear()
    location.reload()
}

function saveLoginDetails(username, password, isAdmin) {
    localStorage.setItem("username", username)
    localStorage.setItem("password", password)
    localStorage.setItem("admin", isAdmin)
}

function baseAuthHeader() {
    var tok = localStorage.getItem("username") + ':' + localStorage.getItem("password");
    var enc = btoa(tok);
    return "Basic " + enc;
}

function loadNavbar() {
    greeting = `
        <button type="button" class="btn btn-outline-success" data-bs-toggle="modal" data-bs-target="#loginFormModal">
            Log in
        </button>
    `

    usersUrl = ""

    if(localStorage.getItem("username") != undefined) {
        greeting = `
            <div id="helloUser" class="mx-3">
                <p class="m-0">Hello, ` + localStorage.getItem("username") + `!</p>
            </div>
            <button type="button" class="btn btn-outline-danger" onclick="logout()">
                Log out
            </button>
        `
    }

    if(localStorage.getItem("admin") === 'true') {
        usersUrl = `
            <li class="nav-item ">
                <a class="nav-link" href="/users.html">Users</a>
            </li>
        `
    }

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

                    ` + usersUrl + `
                    </ul>

                    ` + greeting + `

                    <div class="modal fade" id="loginFormModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="loginFormModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                            <h1 class="modal-title fs-5" id="loginFormModalLabel">Log in</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form action="" id="loginForm">
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
                            <button class="btn btn-secondary" data-bs-target="#registerFormModal" data-bs-toggle="modal">Register</button>
                            <button type="button" class="btn btn-primary" onclick="login()">Log in</button>
                            </div>
                        </div>
                        </div>
                    </div>


                </div>
                <div class="modal fade" id="registerFormModal" aria-hidden="true" aria-labelledby="exampleModalToggleLabel2" tabindex="-1">
                    <div class="modal-dialog ">
                        <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalToggleLabel2">Register</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="" id="registerForm">

                                <div class="mb-3 row">
                                    <label for="inputUsernameReg" class="col-md-2 col-form-label">Username</label>
                                    <div class="col-md-10">
                                        <input type="text" class="form-control" maxlength="30" id="inputUsernameReg">
                                    </div>
                                </div>

                                <div class="mb-3 row">
                                    <label for="inputPasswordReg" class="col-md-2 col-form-label">Password</label>
                                    <div class="col-md-10">
                                        <input type="password" class="form-control" maxlength="30" id="inputPasswordReg">
                                    </div>
                                </div>

                            </form>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-primary" data-bs-target="#loginForm" data-bs-toggle="modal">Back to log in</button>
                            <button type="button" class="btn btn-primary" onclick="register()">Register</button>
                        </div>
                        </div>
                    </div>
                </div>




                </div>
            </div>
        </nav>
    `)
}