function getAllRooms() {
    cleanRoomList()
    $.ajax({
        url: Config().API_URL + '/rooms',
        error: function() {
            $("#room-list").append(`
                <div class="alert alert-warning" role="alert">
                    Could not load rooms, please try again later!
                </div>
            `)
        },
        success: function(data, status) {
            renderRooms(data)
        }
    })
}

function getFilteredRooms() {
    let typeFilter = $("#roomTypeFilter").find(":selected").val()

    filters = {}

    if(typeFilter !== "all") {
        filters.type = typeFilter
    }else{
        getAllRooms()
        return
    }

    cleanRoomList()
    $.ajax({
        url: Config().API_URL + '/rooms/filter',
        data: filters,
        error: function() {
            $("#rooms-list").append(`
                <div class="alert alert-warning" role="alert">
                    Could not load users, please try again later!
                </div>
            `)
        },
        success: function(data, status) {
            renderRooms(data)
        }
    })
}

function addRoom() {
    dataType = "json"
    var $form = $("#addRoom")

    var data = {
        "name": $form.find( "input[id='roomName']" ).val(),
        "seats": $form.find( "input[id='inputSeats']" ).val(),
        "type": $form.find( "select[id='roomTypeAdd']" ).val()
    }

    console.log(data)
    console.log(headers)
    $.ajax({
        url: Config().API_URL + '/rooms',
        type: "POST",
        data: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            "Authorization": baseAuthHeader()
        },
        error: function() {
            alert("Could not create room")
        },
        success: function(data, status) {
            alert("Room created")
            getAllRooms()
            removeModal('#roomAddModal')

        },
        dataType: dataType
    })
}

function removeModal(modal_id){
    $(modal_id).modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();

}

function editRoom(id) {
    dataType = "json"
    var $form = $("#editRoom"+ id)

    var data = {
        "name": $form.find( "input[id='roomName" + id + "']" ).val(),
        "seats": $form.find( "input[id='inputSeats" + id + "']" ).val(),
        "type": $form.find( "select[id='roomTypeAdd" + id + "']" ).val()
    }

    console.log(data)
    $.ajax({
        url: Config().API_URL + '/rooms/'+ id,
        type: "PUT",
        data: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            "Authorization": baseAuthHeader()
        },
        error: function() {
            alert("Could not edit room")
        },
        success: function(data, status) {
            alert("Room edited")
            $('.modal-backdrop').remove();
            getAllRooms()
        },
        dataType: dataType
    })
}

function removeRoom(id) {
    $.ajax({
        url: Config().API_URL + '/rooms/'+ id,
        type: "DELETE",
        headers: {
            "Authorization": baseAuthHeader()
        },
        error: function() {
            alert("Could not delete room")
        },
        success: function(data, status) {
            alert("Room deleted")
            getAllRooms()
        }
    })
}

function renderRooms(data) {
    if(data.length != 0) {
        for (const key in data) {
            let room = data[key]

            let roomSelected2d = ""
            let roomSelected3d = ""

            if(room.type === "R_2D") {
                roomSelected2d = "checked"
            }else{
                roomSelected3d= "checked"
            }

            $("#room-list").append(`
                <li class="list-group-item d-flex justify-content-between align-items-start">
                    <div class="ms-2 me-auto">
                        <p class="fw-bold">` + room.name + `</p>
                        <p>Seats: `+ room.seats + `</p>
                        <p>Room type: ` + room.type + `</p>
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#roomEditModal` + room.id + `">
                            Edit
                        </button>

                        <!-- Modal -->
                        <div class="modal fade" id="roomEditModal` + room.id + `" tabindex="-1" aria-labelledby="roomEditModalLabel` + room.id + `" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" id="roomEditModalLabel` + room.id + `">Add room</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="" id="editRoom`+room.id+`">
                                            <div class="mb-3 row">
                                                <label for="roomName` + room.id + `" class="col-sm-2 col-form-label">Name</label>
                                                <div class="col-sm-10">
                                                    <input type="text"  class="form-control" id="roomName` + room.id + `" value="` + room.name + `">
                                                </div>
                                            </div>

                                            <div class="mb-3 row">
                                                <label for="inputSeats` + room.id + `" class="col-sm-2 col-form-label">Seats</label>
                                                <div class="col-md-10">
                                                    <input value="` + room.seats + `" type="number" class="form-control" min="10" max="3"  id="inputSeats` + room.id + `">
                                                </div>
                                            </div>
                                            <div class="mb-3 ml-2 row">

                                                <label for="roomTypeAdd` + room.id + `" class="col-sm-2 col-form-label">Room type</label>
                                                <div class="col-md-10">
                                                    <select class="form-select" id="roomTypeAdd` + room.id + `" aria-label="Room Type">
                                                        <option value="R_2D" `+ roomSelected2d +` >2D</option>
                                                        <option value="R_3D" `+ roomSelected3d +` >3D</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    <button type="button" class="btn btn-primary" onclick="editRoom(`+room.id+`)">Save changes</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <button type="button" class="btn btn-danger" onclick="removeRoom(` + room.id + `)">Delete</button>
                    </div>
                </li>
            `)
        }
    } else {
        $("#room-list").append(`
            <div class="alert alert-light" role="alert">
                No rooms available!
            </div>
        `)
    }
}

function cleanRoomList() {
    $("#room-list").empty()
}
