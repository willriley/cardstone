<!DOCTYPE html>
	<head>
		<link rel="stylesheet" href="css/resZone.css">
		<link rel="stylesheet" href="css/bootstrap.css">
		<link rel="stylesheet" href="css/cardBack.css">
        <link rel="stylesheet" href="css/deck.css">
		<link rel="stylesheet" href="css/cardStyle.css">
		<link rel="stylesheet" href="css/smallCard.css">
		<link rel="stylesheet" href="css/bigCard.css">
		<link rel="stylesheet" href="css/tinyCard.css">
        <link rel="stylesheet" href="css/deckList.css">
		<link rel="stylesheet" href="qtip.css">
	</head>
	<body>
    <div class="board">
        <div class="deckContent">
            <div class="zone pageLeft"></div>
            <div class="zone collectionDisplay"></div>
            <div class="zone pageRight"></div>
            <div class="zone deckList"></div>
            <div class="zone controls">
            <table><tr><td>
                <input id="filterText" placeholder="Filter by:" type="text"></input>
                <button id="filterSubmit">Filter!</button>
            </table></tr></td>
            </div>
            <div class="zone deckData">
                <div id="progressBar">
                <br>
                 <input id="deckName" placeholder="Deck Name"></input>
                 <br>
                 <button id="deckSubmit">Submit!</button>
            </div>
        </div>
    </div>
        <script type="text/javascript">
           <#if deck?? && deckname??>
              let cardNames = ${deck};
			  let nameOfDeck = "${deckname}";
		   </#if>
	 	</script>
        <script src="js/jquery-2.1.1.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
        <script src="js/animationsMaker.js"></script>
        <script src="js/Enums.js"></script>
        <script src="js/tether.min.js"></script>
        <script src="js/bootstrap.js"></script>
        <script src="js/cardCacher.js"></script>
        <script src="js/drawable.js"></script>
        <script src="js/animation.js"></script>
        <script src="js/radialAnimation.js"></script>
        <script src="js/movingDrawable.js"></script>
        <script src="js/linearAnimation.js"></script>
        <script src="js/manaPool.js"></script>
        <script src="js/cost.js"></script>
        <script src="js/card.js"></script>
        <script src="js/drawableZone.js"></script>
        <script src="js/healthResZone.js"></script>
        <script src="js/chooseZone.js"></script>
        <script src="js/deckList.js"></script>
        <script src="js/cardCollectionDeck.js"></script>
        <script src="js/board.js"></script>
        <script src="js/deckDrawing.js"></script>
        <script src="js/server.js"></script>
        <script src="js/qtip.js"></script>
	</body>
</html>