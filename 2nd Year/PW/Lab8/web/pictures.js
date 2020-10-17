$(document).ready(function () {
    let userid = $("#userid").val();
    $("#yourpics").click(function () {
        nowViewing("your gallery");
        $("#results").load("PictureController", {
            action: "yourpics",
            userid: userid
        });


    });
    $("#gallery").click(function () {
        getPictures(userid, 0, function (pictures) {
            let vote = "<label for=\"radio1\">1</label>\n" +
                "\t<input type=\"radio\" name=\"radio\" value=\"1\">\n" +
                "\t<label for=\"radio2\">2</label>\n" +
                "\t<input type=\"radio\" name=\"radio\" value=\"2\">\n" +
                "\t<label for=\"radio3\">3</label>\n" +
                "\t<input type=\"radio\" name=\"radio\" value=\"3\">\n" +
                "\t<label for=\"radio4\">4</label>\n" +
                "\t<input type=\"radio\" name=\"radio\" value=\"4\">\n" +
                "\t<label for=\"radio5\">5</label>\n" +
                "\t<input type=\"radio\" name=\"radio\" value=\"5\">\n" +
                "\t<button type='submit'>Vote</button>";

            $("#results").html("");
            for (let name in pictures) {
                let final = "<form class='filter_forms' action='PictureController' >" +
                    "<input type=\"hidden\" name=\"id_picture\" value=\""
                    + pictures[name].id_picture + "\"><img src=\"" + pictures[name].path + "\" id=\"" + pictures[name].id_picture + "\"><br>";
                if(userid == pictures[name].id_user){
                    final += "</form>";
                }
                else{
                    final += vote + "</form>";
                }
                $("#results").append(final);
            }
        });
        nowViewing("all pictures");
    });
    $("#top5").click(function () {
        getTop(userid, 5);
        nowViewing("top 5");
    });
    $("#top10").click(function () {
        getTop(userid, 10);
        nowViewing("top 10");
        // $("#results").load("PictureController", {
        //     action: "top",
        //     userid: userid,
        //     value: 10
        // });
    });
    $("#show").click(function () {
        let custom_top = $("#in").val();
        if (custom_top == ""){
            custom_top = 0;
        }
        getTop(userid, custom_top);
        nowViewing("top " + custom_top)
    });
    $("#results").on("submit", ".filter_forms", function (e) {
        e.preventDefault();
        let radio = $("input[name=radio]:checked").val();
        let id_picture = $(this).find("img").attr("id");
        $(this).find("button").prop("disabled", true);
        $.get("PictureController", {
            action: "vote",
            userid: userid,
            id_picture: id_picture,
            score: radio
        }, function (text) {
            alert(text);
        });
    });

});

function nowViewing(value){
    $("#current").html("Now viewing: " + value);
}

function getTop(userid, value) {
    $("#results").load("PictureController", {
        action: "top",
        userid: userid,
        value: value
    });
}

function getPictures(userid, count, callbackFunction) {
    $.getJSON(
        "PictureController",
        { action: 'gallery', userid: userid, count: count },
        callbackFunction
    );
}

