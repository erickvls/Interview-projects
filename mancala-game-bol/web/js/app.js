const newGameUrl = "http://localhost:8080/api/v1/mancala/start";
const moveUrl = "http://localhost:8080/api/v1/mancala/move";

$(document).ready(function ()
{
	$(".new-game").click(newGame);
	$(".pit").click(moveGame);
});

function newGame() {
	var playerOne = prompt("Player one name:");
	var playerTwo = prompt("Player two name:");
	var request = { playerOneName: playerOne, playerTwoName: playerTwo };

	$.ajax({
		type: "POST",
		url: newGameUrl,
		async: false,
		data: JSON.stringify(request),
		contentType: "application/json",
		complete: function (data) {
			$(".status").css("display","block");
			$(".p-game").css("display","block");
			$(".player-name").css("display","block");
			renderGame(data.responseJSON);
		},
		error: function(data){
			$(".message").html(data.statusText);
		}
	});
}

function moveGame() {
	if (!$(this.parentElement).hasClass("turn")) return;

	var pit = this.id.replace("pit","");
	var gameID = $(".game-id").text();
	var request = { pit: parseInt(pit), gameId: gameID }

	$.ajax({
		type: "PUT",
		url: moveUrl,
		async: false,
		data: JSON.stringify(request),
		contentType: "application/json",
		complete: function (data) {
			renderGame(data.responseJSON);
		},
		error: function(data){
			$(".message").html(data.responseJSON.message);
		}
	});
}

function renderGame(game) {
	for (var i = 0; i < game.board.pitList.length; i++) {
		var id = `#pit${i + 1}`;
		$(id).text(game.board.pitList[i].stones);
	}
	$(".game-id").html(game.id);
	$("#player-one-name").html(game.players[0].name);
	$("#player-two-name").html(game.players[1].name);
	setTurn(game);
}

function setTurn(game) {
	var currentPlayer = game.players[0].turn ? game.players[0].name : game.players[1].name;
	$(".current-player").text(currentPlayer);

	if (game.players[0].turn) {
		$(".player-one").addClass("turn");
		document.getElementsByTagName("body")[0].setAttribute("data-player", "one");
		$(".player-two").removeClass("turn");
	}
	else
	{
		$(".player-two").addClass("turn");
		document.getElementsByTagName("body")[0].setAttribute("data-player", "two");
		$(".player-one").removeClass("turn");
    }
}

function init_pits(player, row) {

	for (var pit = 0; pit < row.length; pit++) {
		row[pit].setAttribute('data-pit', pit);
	}
}
