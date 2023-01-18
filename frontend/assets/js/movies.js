function getAllMovies() {
    cleanMovieList()
    $.ajax({
        url: Config().API_URL + '/movies',
        error: function() {
            $("#movie-list").append(`
                <div class="alert alert-warning" role="alert">
                    Could not load movies, please try again later!
                </div>
            `)
        },
        success: function(data, status) {
            renderMovies(data)
        }
    })
}

function getFilteredMovies() {
    let title = $("#movieTitleFilter").val()
    let genre = $("#movieGenreFilter").val()
    let yearFilter = $("#movieYearFilter").val()

    filters = {}

    if(title != "") {
        filters.title = title
    }
    if(genre != "") {
        filters.genre = genre
    }
    if (yearFilter != "") {
        filters.releaseYear = yearFilter
    }

    if(Object.keys(filters).length == 0) {
        getAllMovies()
        return
    }

    cleanMovieList()
    $.ajax({
        url: Config().API_URL + '/movies/filter',
        data: filters,
        error: function() {
            $("#movies-list").append(`
                <div class="alert alert-warning" role="alert">
                    Could not load users, please try again later!
                </div>
            `)
        },
        success: function(data, status) {
            renderMovies(data)
        }
    })
}

async function addMovie() {
    dataType = "json"
    var $form = $("#addMovie")

    encodedImage = await imageToBase64($form.find( "input[id='inputImage']" )[0].files[0])

    var data = {
        "title": $form.find( "input[id='inputTitle']" ).val(),
        "img": encodedImage,
        "genre": $form.find( "input[id='inputGenre']" ).val(),
        "duration":  $form.find( "input[id='inputDuration']" ).val(),
        "description": $form.find( "input[id='inputDescription']" ).val() ,
        "releaseYear":  $form.find( "input[id='inputYear']" ).val() ,
        "language":$form.find( "input[id='inputLang']" ).val() ,
        "trailerUrl": $form.find( "input[id='inputTrailer']" ).val()

    }

    

    $.ajax({
        url: Config().API_URL + '/movies',
        type: "POST",
        data: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            "Authorization": baseAuthHeader()
        },
        error: function() {
            alert("Could not create movie")
        },
        success: function(data, status) {
            alert("Movie created")
            getAllMovies()
            removeModal('#movieAddModal')

        },
        dataType: dataType
    })
}

function removeModal(modal_id){
    $(modal_id).modal('hide');
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();

}

async function editMovie(id) {
    dataType = "json"
    var $form = $("#editMovie"+ id)

    encodedImage = await imageToBase64($form.find( "input[id='inputImage" + id + "']" )[0].files[0])

    var data = {
        "title": $form.find( "input[id='inputTitle" + id + "']" ).val(),
        "img": encodedImage,
        "genre": $form.find( "input[id='inputGenre" + id + "']" ).val(),
        "duration":  $form.find( "input[id='inputDuration" + id + "']" ).val(),
        "description": $form.find( "input[id='inputDescription" + id + "']" ).val() ,
        "releaseYear":  $form.find( "input[id='inputYear" + id + "']" ).val() ,
        "language":$form.find( "input[id='inputLang" + id + "']" ).val() ,
        "trailerUrl": $form.find( "input[id='inputTrailer" + id + "']" ).val()
    }

    console.log(data)
    $.ajax({
        url: Config().API_URL + '/movies/'+ id,
        type: "PUT",
        data: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json',
            "Authorization": baseAuthHeader()
        },
        error: function() {
            alert("Could not edit movie")
        },
        success: function(data, status) {
            alert("Movie edited")
            $('.modal-backdrop').remove();
            getAllMovies()
        },
        dataType: dataType
    })
}

function removeMovie(id) {
    $.ajax({
        url: Config().API_URL + '/movies/'+ id,
        type: "DELETE",
        headers: {
            "Authorization": baseAuthHeader()
        },
        error: function() {
            alert("Could not delete movie")
        },
        success: function(data, status) {
            alert("Movie deleted")
            getAllMovies()
        }
    })
}

function cleanMovieList() {
    $("#movie-list").empty()
}

function imageToBase64(file) {
    return new Promise(function(resolve, reject) {
        var reader = new FileReader();
        reader.onload = function() { resolve(reader.result); };
        reader.onerror = reject;
        reader.readAsDataURL(file);
    });
}
function loadAddBtn(){


    if(localStorage.getItem("admin") === 'true') {

        $("#addMovieBtn").append(`

        <!-- Button trigger modal -->
        <button type="button" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#movieAddModal">
            Add
        </button>

        <!-- Modal -->
        <div class="modal fade" id="movieAddModal" tabindex="-1" aria-labelledby="movieAddModalLabel" aria-hidden="true">
            <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                <h1 class="modal-title fs-5" id="movieAddModalLabel">Add movie</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="" id="addMovie">
                        <div class="mb-3 row">
                            <label for="inputTitle" class="col-sm-2 col-form-label">Title</label>
                            <div class="col-sm-10">
                                <input type="text"  class="form-control" maxlength="30" id="inputTitle" value="">
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label for="inputImage" class="col-md-2 col-form-label">Image</label>
                            <div class="col-md-10">
                                <input type="file" accept="image/*" class="form-control" maxlength="30" id="inputImage">
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label for="inputGenre" class="col-md-2 col-form-label">Genre</label>
                            <div class="col-md-10">
                                <input type="text" class="form-control" maxlength="30" id="inputGenre">
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label for="inputDuration" class="col-md-2 col-form-label">Duration</label>
                            <div class="col-md-10">
                                <input type="text" class="form-control" maxlength="30" id="inputDuration">
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label for="inputYear" class="col-md-2 col-form-label">Release Year</label>
                            <div class="col-md-10">
                                <input type="text" class="form-control" maxlength="4" id="inputYear">
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label for="inputLang" class="col-md-2 col-form-label">Language</label>
                            <div class="col-md-10">
                                <input type="text" class="form-control" maxlength="30" id="inputLang">
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label for="inputTrailer" class="col-md-2 col-form-label">Trailer Url</label>
                            <div class="col-md-10">
                                <input type="text" class="form-control" maxlength="200" id="inputTrailer">
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <label for="inputDescription" class="col-md-2 col-form-label">Description</label>
                            <div class="col-md-10">
                                <textarea class="form-control" rows="3"  id="inputDescription"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="addMovie()">Save</button>
                </div>
            </div>
            </div>
        </div>

        `)

    }

}
function renderMovies(data) {
    if(data.length != 0) {
        for (const key in data) {
            let movie = data[key]

            adminInterface = ""
            if(localStorage.getItem("admin") === 'true') {
                adminInterface = `
                    <!-- Modal -->
                    <div class="modal fade" id="movieEditModal`+ movie.id +`" tabindex="-1" aria-labelledby="movieEditModal`+ movie.id +`Label" aria-hidden="true">
                        <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                            <h1 class="modal-title fs-5" id="movieEditModal`+ movie.id +`Label">Edit movie</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form action="" id="editMovie`+movie.id+`">
                                    <div class="mb-3 row">
                                        <label for="inputTitle`+movie.id+`" class="col-sm-2 col-form-label">Title</label>
                                        <div class="col-sm-10">
                                            <input  value="`+movie.title+`" type="text"  class="form-control" maxlength="30" id="inputTitle`+movie.id+`" value="">
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label for="inputImage`+movie.id+`" class="col-md-2 col-form-label">Image</label>
                                        <div class="col-md-10">
                                            <input value="`+movie.img+`" type="file" accept="image/*" class="form-control" maxlength="30" id="inputImage`+movie.id+`">
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label for="inputGenre`+movie.id+`" class="col-md-2 col-form-label">Genre</label>
                                        <div class="col-md-10">
                                            <input  value="`+ movie.genre +`" type="text" class="form-control" maxlength="30" id="inputGenre`+movie.id+`">
                                        </div>
                                    </div>

                                    <div class="mb-3 row">
                                        <label for="inputDuration`+movie.id+`" class="col-md-2 col-form-label">Duration</label>
                                        <div class="col-md-10">
                                            <input  value="`+ movie.duration +`" type="text" class="form-control" maxlength="30" id="inputDuration`+movie.id+`">
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label for="inputYear`+movie.id+`" class="col-md-2 col-form-label">Release Year</label>
                                        <div class="col-md-10">
                                            <input  value="`+ movie.releaseYear +`" type="text" class="form-control" maxlength="4" id="inputYear`+movie.id+`">
                                        </div>
                                    </div>

                                    <div class="mb-3 row">
                                        <label for="inputLang`+movie.id+`" class="col-md-2 col-form-label">Language</label>
                                        <div class="col-md-10">
                                            <input  value="`+ movie.language +`" type="text" class="form-control" maxlength="30" id="inputLang`+movie.id+`">
                                        </div>
                                    </div>
                                    <div class="mb-3 row">
                                        <label for="inputTrailer`+movie.id+`" class="col-md-2 col-form-label">Trailer Url</label>
                                        <div class="col-md-10">
                                            <input  value="`+ movie.trailerUrl +`" type="text" pattern="https?://.+" class="form-control" maxlength="200" id="inputTrailer`+movie.id+`">
                                        </div>
                                    </div>


                                    <div class="mb-3 row">
                                        <label for="inputDescription`+movie.id+`" class="col-md-2 col-form-label">Description</label>
                                        <div class="col-md-10">
                                            <textarea class="form-control" rows="3"  id="inputDescription`+movie.id+`">`+ movie.description+`</textarea>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer ">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" onclick="editMovie(` + movie.id + `)">Save changes</button>
                            </div>
                        </div>
                        </div>
                    </div>
                    <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#movieEditModal`+ movie.id +`">
                        Edit
                    </button>
                    <button type="button" class="btn btn-danger" onclick="removeMovie(` + movie.id + `)">Delete</button>
                `
            }

            $("#movie-list").append(`
                <li class="list-group-item d-flex justify-content-between align-items-start  mb-1"  >
                    <div class="col-ms-2">
                        <img src=" ` + movie.img + `" class="img-thumbnail rounded-start"alt="...">
                    </div>
                    <div class="ms-2 me-auto">
                        <div class="fw-bold">` + movie.title + `</div>
                        <small class="d-flex fst-italic fw-lighter text-wrap ">
                        Genre: ` + movie.genre + `
                        </small>
                        <small class="d-flex fst-italic fw-lighter text-wrap ">
                        Duration: ` + movie.duration + `
                        </small>

                        <small class="d-flex fst-italic fw-lighter text-wrap ">
                        Release Year: ` + movie.releaseYear + `
                        </small>
                        <small class="d-flex fst-italic fw-lighter text-wrap ">
                        Language: ` + movie.language + `
                        </small>
                        <small class="d-flex fst-italic fw-lighter text-wrap ">
                        Trailer: <a href="` + movie.trailerUrl + `" target="blank">Click here</a>
                        </small>

                        <p>
                            <button class="btn btn-link" type="button" data-bs-toggle="collapse" data-bs-target="#movieDesc`+ key +`" aria-expanded="false" aria-controls="movieDesc`+ key +`">
                                Description
                            </button>
                        </p>
                        <div class="collapse" id="movieDesc`+ key +`">
                            <div class="card card-body">
                                `+ movie.description +`
                            </div>
                        </div>
                        <div class="card-footer ">

                            <!-- Button trigger modal -->
                            ` + adminInterface + `
                        </div>
                    </div>
                </li>
            `)
        }
    } else {
        $("#movie-list").append(`
            <div class="alert alert-light" role="alert">
                No movies available!
            </div>
        `)
    }

}
