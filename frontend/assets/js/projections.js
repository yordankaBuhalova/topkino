function getAllProjections() {
    cleanProjectionList()
    $.ajax({
        url: Config().API_URL + '/projections',
        error: function() {
            $("#projection-list").append(`
                <div class="alert alert-warning" role="alert">
                    Could not load projections, please try again later!
                </div>
            `)
        },
        success: function(data, status) {
            renderProjections(data)
        }
    })
}

function addProjection() {
    dataType = "json"
    var $form = $("#addProjection")

    var data = {
        "movie": $form.find( "select[id='inputMovie']" ).val(),
        "projectionOn": $form.find( 'input[type="datetime-local"]' ).val(),
        "room": $form.find( "select[id='inputRoom']" ).val(),
        "price":  $form.find( "input[id='inputPrice']" ).val(),

    }

    $.ajax({
        url: Config().API_URL + '/projections',
        type: "POST",
        data: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            "Authorization": baseAuthHeader()
        },
        error: function() {
            alert("Could not create projection")
        },
        success: function(data, status) {
            alert("Projection created")
            getAllProjections()
            removeModal('#projectionAddModal')

        },
        dataType: dataType
    })
}

function editProjection(id) {
    dataType = "json"
    var $form = $("#editProjection"+ id)

    var data = {
        "movie": $form.find( "select[id='inputMovie" + id + "']" ).val(),
        // convert ISO to unix timestamp
        "projectionOn": Math.round(new Date($form.find( "input[id='meeting-time" + id + "']" ).val()).getTime()),
        "room": $form.find( "select[id='inputRoom" + id + "']" ).val(),
        "price":  $form.find( "input[id='inputPrice" + id + "']" ).val(),
    }

    console.log(data)
    $.ajax({
        url: Config().API_URL + '/projections/'+ id,
        type: "PUT",
        data: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            "Authorization": baseAuthHeader()
        },
        error: function() {
            alert("Could not edit projection")
        },
        success: function(data, status) {
            alert("Projection edited")
            $('.modal-backdrop').remove();
            getAllProjections()
        },
        dataType: dataType
    })
}

function removeProjection(id) {
    $.ajax({
        url: Config().API_URL + '/projections/'+ id,
        type: "DELETE",
        headers: {
            "Authorization": baseAuthHeader()
        },
        error: function() {
            alert("Could not delete projection")
        },
        success: function(data, status) {
            alert("Projection deleted")
            getAllProjections()
        }
    })
}

function loadMovies(listID, selectedMovie) {
    $.ajax({
        url: Config().API_URL + '/movies',
        error: function() {
            $("#"+listID).append(`
                <option disabled>N/A</option>
            `)
        },
        success: function(data, status) {
            for (const key in data) {
                let movie = data[key]
                selected = ""
                if(selectedMovie == movie.id) {
                    selected = "selected"
                }

                $("#"+listID).append(`
                    <option value="` + movie.id + `" ` + selected + `>` + movie.title + `</option>
                `)
            }
        }
    })
}

function loadRooms(listID, selectedRoom) {
    $.ajax({
        url: Config().API_URL + '/rooms',
        error: function() {
            $("#"+listID).append(`
                <option disabled>N/A</option>
            `)
        },
        success: function(data, status) {
            for (const key in data) {
                let room = data[key]
                selected = ""
                if(selectedRoom == room.id) {
                    selected = "selected"
                }

                $("#"+listID).append(`
                    <option value="` + room.id + `" ` + selected + `>` + room.name + `</option>
                `)

            }
        }
    })
}

function removeModal(modal_id){
    $(modal_id).modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();

}

function loadAddBtn(){


    if(localStorage.getItem("admin") === 'true') {

        $("#addBtnProjection").append(`
        <!-- Button trigger modal -->

        <button type="button" class="btn btn-dark mx-3" data-bs-toggle="modal" data-bs-target="#projectionAddModal">
            Add
        </button>

        <!-- Modal -->
        <div class="modal fade" id="projectionAddModal" tabindex="-1" aria-labelledby="projectionAddModalLabel" aria-hidden="true">
            <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                <h1 class="modal-title fs-5" id="projectionAddModalLabel">Add projection</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body mx-3">
                    <form action="" id="addProjection">
                        <div class="mb-3 row">
                            <label for="inputMovie" class="col-sm-2 col-form-label">Movie:</label>
                            <select class="form-select" id="inputMovie" aria-label="Movie"></select>
                        </div>

                        <div class="mb-3 row">
                            <label for="inputRoom" class="col-sm-2 col-form-label">Room:</label>
                            <select class="form-select" id="inputRoom" aria-label="Room"></select>
                        </div>
                        <div class="mb-3 row">
                            <div class="col-md-12">

                                <label for="meeting-time" class="col-form-label">Choose a time for your projection:</label>

                                    <input type="datetime-local" id="meeting-time" class="form-control"
                                        name="meeting-time" value=""
                                        min="2018-06-07T00:00" max="2025-06-14T00:00">
                            </div>
                        </div>
                        <div class="mb-3 row">
                            
                            <div class="col-md-12">
                                <label for="inputPrice" class="col-sm-2 col-form-label">Price:</label>
                                <input type="number" class="form-control"  id="inputPrice">
                            </div>
                        </div>
                    </form>
                </div>

                <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary"onclick="addProjection()">Save changes</button>
                </div>
            </div>
            </div>
        </div

        `)

    }

}

function getFilteredProjections() {
    cleanProjectionList()
    let movie = $("#movieTitleFilter").val()
    let dateFilter = $("#dateFilter").val()

    filters = {
    }

    if(movie != "") {
        filters.movie = parseInt(movie)
    }
    // if(genre != "") {
    //     filters.genre = genre
    // }
    if (dateFilter != "") {
        filters.projectionOn = new Date(dateFilter).toISOString().slice(0,10) + " 00:00:00"
    }
    if(Object.keys(filters).length == 0) {
        getAllProjections()
        return
    }

    cleanProjectionList()
    $.ajax({
        url: Config().API_URL + '/projections/filter',
        data: filters,
        error: function() {
            $("#movies-list").append(`
                <div class="alert alert-warning" role="alert">
                    Could not load users, please try again later!
                </div>
            `)
        },
        success: function(data, status) {
            renderProjections(data)
        }
    })
}

function renderProjections(data) {
    if(data.length != 0) {
        for (const key in data) {
            let projection = data[key]
            let roomName = ""
            let movieName = ""

            $.ajax({
                url: Config().API_URL + '/rooms/' + projection.room,
                success: function(data, status) {
                    roomName = data.name
                },
                async: false
            })

            $.ajax({
                url: Config().API_URL + '/movies/' + projection.movie,
                success: function(data, status) {
                    movieName = data.title
                    movieImage = data.img
                },
                async: false
            })
            editProjectionModalForms = ""

            if(localStorage.getItem("admin") === 'true') {
                editProjectionModalForms = `
                <!-- Button trigger modal -->
                <button type="button" class="btn btn-warning mx-3" data-bs-toggle="modal" data-bs-target="#projectionEditModal`+projection.id+`">
                    Edit
                </button>

                <!-- Modal -->
                <div class="modal fade" id="projectionEditModal`+projection.id+`" tabindex="-1" aria-labelledby="projectionEditModalLabel`+projection.id+`" aria-hidden="true">
                    <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                        <h1 class="modal-title fs-5" id="projectionEditModalLabel`+projection.id+`">Edit projection</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body mx-3">
                            <form action="" id="editProjection`+ projection.id+`">
                            <div class="mb-3  row">
                                
                                <div class="col-sm-12">
                                <label for="inputMovie`+ projection.id +`" class="col-sm-2 col-form-label">Movie:</label>
                                <select class="form-select" id="inputMovie`+ projection.id +`" aria-label="Movie">
                                </select>
                                </div>
                            </div>

                            <div class="mb-3  row">
                                
                                <div class="col-sm-12">
                                <label for="inputRoom`+ projection.id +`" class="col-sm-2 col-form-label">Room:</label>
                                <select class="form-select" id="inputRoom`+ projection.id +`" aria-label="Room"></select>
                                </div>
                            </div>
                            <div class="mb-3 row">
                                
                                <div class="col-md-12">

                                    <label for="meeting-time`+projection.id+`" class=" col-form-label">Choose a time for your projection:</label>

                                        <input type="datetime-local" id="meeting-time`+ projection.id +`" class="form-control"
                                            name="meeting-time`+projection.id+`" value="`+ new Date(projection.projectionOn).toISOString().slice(0, -1)+`"
                                            min="2018-06-07T00:00" max="2025-06-14T00:00">

                                </div>
                            </div>
                            <div class="mb-3 row">
                                
                                <div class="col-md-12">
                                    <label for="inputPrice`+projection.id+`" class="col-sm-2 col-form-label">Price:</label>
                                    <input type="number" class="form-control"  id="inputPrice`+projection.id+`" value="`+projection.price+`">
                                </div>
                            </div>
                            </form>
                        </div>

                        <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" onclick="editProjection(`+ projection.id +`)">Save changes</button>
                        </div>
                    </div>
                    </div>
                </div>

                        <button type="button" class="btn btn-danger" onclick="removeProjection(` + projection.id + `)">Delete</button>
                    </div>

                `
            }
            orderBtn=""
            if(localStorage.getItem("username") != undefined) {
                orderBtn = `


                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#reservationModal">
                    Make reservation
                    </button>

                    <!-- Modal -->
                    <div class="modal fade" id="reservationModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="reservationModalLabel" aria-hidden="true">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h1 class="modal-title fs-5" id="reservationModalLabel">Make reservation</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                          </div>
                          <div class="modal-body">
                            ...
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary">Send</button>
                          </div>
                        </div>
                      </div>
                    </div>
                `
            }

            $("#projection-list").append(`
                <li class="list-group-item d-flex justify-content-between align-items-start">
                    <div class="col-ms-2">
                        <img src=" ` + movieImage + `" class="img-thumbnail rounded-start" alt="...">
                    </div>
                    <div class="ms-2 me-auto">
                        <p class="fw-bold">` + movieName + `</p>
                        <p>Room: `+ roomName + `</p>
                        <p>Time: `+ new Date(projection.projectionOn).toLocaleString() + `</p>
                        <p>price: ` + projection.price + ` lv. </p>

                        `+editProjectionModalForms + orderBtn +`


                </li>
            `)
            loadRooms("inputRoom"+ projection.id, projection.room)
            loadMovies("inputMovie"+ projection.id, projection.movie)
        }
    } else {
        $("#projection-list").append(`
            <div class="alert alert-light" role="alert">
                No projections available!
            </div>
        `)
    }
}


function cleanProjectionList() {
    $("#projection-list").empty()
}
