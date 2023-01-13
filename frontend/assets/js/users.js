function getAllUsers() {
    cleanUserList()
    $.ajax({
        url: Config().API_URL + '/users',
        error: function() {
            $("#users-list").append(`
                <div class="alert alert-warning" role="alert">
                    Could not load users, please try again later!
                </div>
            `)
        },
        success: function(data, status) {
            renderUsers(data)
        }
    })
}

function getFilteredUsers() {
    let roleFilter = $("#userRoleFilter").find(":selected").val()

    filters = {}

    if(roleFilter === "admin") {
        filters.admin = true
    }
    else if (roleFilter === "nonadmin") {
        filters.admin = false
    }

    cleanUserList()
    $.ajax({
        url: Config().API_URL + '/users/filter',
        data: filters,
        error: function() {
            $("#users-list").append(`
                <div class="alert alert-warning" role="alert">
                    Could not load users, please try again later!
                </div>
            `)
        },
        success: function(data, status) {
            renderUsers(data)
        }
    })
}

function addUser() {
    dataType = "json"
    var $form = $("#addUser")
    var is_admin = false
    if ($form.find( "input[id='isAdmin']" ).val() === "on"){
        is_admin= true
    }
    var data = {
        "username": $form.find( "input[id='inputUsername']" ).val(),
        "password": $form.find( "input[id='inputPassword']" ).val(),
        "admin": is_admin
    }
    console.log(data)
    $.ajax({
        url: Config().API_URL + '/users',
        type: "POST",
        data: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
        },
        error: function() {
            alert("Could not create user")
        },
        success: function(data, status) {
            alert("User created")
            getAllUsers()
            removeModal('#userAddModal')

        },
        dataType: dataType
    })
}

function editUser(id) {
    dataType = "json"
    var $form = $("#editUser" + id)

    var is_admin = false
    if ($form.find( "input[id='isAdmin "+ id + "']" ).val() === "on"){
        is_admin= true
    }
    var data = {
        "username": $form.find( "input[id='inputUsername"+ id + "']" ).val(),
        "password": $form.find( "input[id='inputPassword"+ id + "']" ).val(),
        "admin": is_admin
    }

    console.log(data)
    $.ajax({
        url: Config().API_URL + '/users/'+ id,
        type: "PUT",
        data: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
        },
        error: function() {
            alert("Could not edit user")
        },
        success: function(data, status) {
            alert("User edited")
            $('.modal-backdrop').remove();
            getAllUsers()
        },
        dataType: dataType
    })
}

function removeModal(modal_id){
    $(modal_id).modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();

}
function removeUser(id) {
    $.ajax({
        url: Config().API_URL + '/users/'+ id,
        type: "DELETE",
        error: function() {
            alert("Could not delete user")
        },
        success: function(data, status) {
            alert("User deleted")
            getAllUsers()
        }
    })
}

function renderUsers(userList) {
    if(userList.length != 0) {
        for (const key in userList) {
            let user = userList[key]
            let adminChecked = ""

            if(user.admin) {
                adminChecked = "checked"
            }

            $("#users-list").append(`
                <li class="list-group-item d-flex justify-content-between align-items-start">
                    <div class="ms-2 me-auto">
                        <p class="fw-bold">` + user.username + `</p>
                        <p>Admin: `+ user.admin + `</p>

                            <!-- Button trigger modal -->
                            <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#userEditModal`+ user.id +`">
                                Edit
                            </button>

                            <!-- Modal -->
                            <div class="modal fade" id="userEditModal`+ user.id +`" tabindex="-1" aria-labelledby="userEditModal`+ user.id +`Label" aria-hidden="true">
                                <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="userEditModal`+ user.id +`Label">Edit user</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                    <form action="" id="editUser`+ user.id +`">
                                        <div class="mb-3 row">

                                            <label for="inputUsername`+ user.id +`" class="col-sm-2 col-form-label">Username</label>
                                            <div class="col-sm-10">
                                                <input type="text"  class="form-control" id="inputUsername`+ user.id +`" value="` + user.username + `">
                                            </div>
                                        </div>

                                        <div class="mb-3 row">
                                            <label for="inputPassword`+ user.id +`" class="col-md-2 col-form-label">Password</label>
                                            <div class="col-md-10">
                                                <input type="password" class="form-control" id="inputPassword`+ user.id +`" value="`+ user.password +`">
                                            </div>
                                        </div>
                                        <div class="mb-3 ml-2 row">
                                            <div class="form-check-reverse col-auto space-style">
                                                <input class="form-check-input" type="checkbox" `+ adminChecked +` id="isAdmin`+ user.id +`">
                                                <label class="form-check-label" for="isAdmin`+ user.id +`">
                                                    Is Admin
                                                </label>

                                            </div>
                                        </div>
                                    </form>
                                    </div>
                                    <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    <button type="button" class="btn btn-primary" onclick="editUser(`+user.id+`)">Save changes</button>
                                    </div>
                                </div>
                                </div>
                            </div>
                        <button type="button" class="btn btn-danger" onclick="removeUser(` + user.id + `)">Delete</button>
                    </div>
                </li>
            `)
        }
    } else {
        $("#users-list").append(`
            <div class="alert alert-light" role="alert">
                No users available!
            </div>
        `)
    }
}

function cleanUserList() {
    $("#users-list").empty()
}


