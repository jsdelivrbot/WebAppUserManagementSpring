<!-- MATERIAL DESIGN FONTS -->
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

<!-- BOOTSTRAP MATERIAL DESIGN -->
<link rel="stylesheet" href="https://cdn.rawgit.com/FezVrasta/bootstrap-material-design/dist/dist/bootstrap-material-design.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<!-- NOTIFICATION SERVICE LATO CLIENT -->
<script>
    function createNotification(type, message, timer, count){

        var div = document.createElement("DIV");
        var button = document.createElement("BUTTON");
        var span = document.createElement("SPAN");

        div.setAttribute("class", "alert alert-"+type+" alert-dismissible fade show");
        div.setAttribute("role", "alert");
        div.setAttribute("id", "error-alert"+count);

        div.innerText=message;

        button.setAttribute("type", "button");
        button.setAttribute("class", "close");
        button.setAttribute("data-dismiss", "alert");
        button.setAttribute("aria-label", "close"+count);

        span.innerHTML="&times;";


        div.appendChild(button);
        button.appendChild(span);

        var divMain = document.getElementById("idNotification");
        divMain.appendChild(div);

        console.log(type,message,timer,count);

        var parsedTimer = parseInt(timer);
        $(div).fadeTo(parsedTimer, 500).slideUp(500);


    }

</script>