class cardCollection extends drawableZone{
	
	
	constructor(div,cards,expandInto){
		super();
		this.div = div;
		this.cards = cards;
		this.changed = true;
		this.prepareForExpand();
		this.expandInto = expandInto;
		this.zone = div.attr("id");
        this.count = 0;
	}
	
	prepareForExpand(){
		let $this = this;
		this.div.children(".expandButton").click(function(){
			if(!expandedInUse){
				expandedInUse = true;
				$this.expand();
			}
		});
	}
	
	setZones(){
		for(let card of this.cards){
			card.setZone(this.zone);
		}
	}
	
	expand(){
		let $this = this;
		$this.expandInto.toggle();
		$this.forceDrawInDiv($this.expandInto);
		$this.expandInto.click(function(){
			$this.expandInto.empty();
			expandedInUse = false;
			$this.expandInto.hide();
			$('div.qtip:visible').qtip('hide');
            $this.forceRedraw();
		});
	}
	
	setCards(cards){
		this.cards = cards;
		this.changed = true;
	}
	
	setCardsFromCache(cardIDs, cache){
		let cards = [];
		for(let id of cardIDs){
			cards.push(cache.getByIID(id));
		}
		this.cards = cards;
	}
	
	forceRedraw(){
		this.changed = true;
		this.draw();
	}
	
	pushCard(card){
		this.cards.push(card);
		this.changed = true;
	}
	
	fillDiv(div){
		let curDiv = div;
		let $this = this;
		if($this.cards.length < 1){
			return;
		}
		else{
		}
		let baseWidth = (curDiv.height() * WIDTH_RATIO) + 3;
		let rows = 1;
		while(baseWidth * ($this.cards.length / rows) >= curDiv.width() * .97){
			rows++;
			baseWidth = ((curDiv.height() / rows) * WIDTH_RATIO) + 3;
		}
		let maxInRow = Math.ceil($this.cards.length / rows);
		if(maxInRow == 0){
			console.log("Problem with row sizing in filldiv");
		}
		let total = 0;
		for(let x = 1; x <= rows; x++){
			curDiv.append('<div class="cardRow"></div');
			let curChild = curDiv.children().last();
			curChild.css('height', (curDiv.height() / rows) + "px");
			while(total < x * maxInRow && total < this.cards.length){
				//space to mekkit mejor
				curChild.append('<div class="cardBox"></div>');
				let cur = curChild.children().last();
				cur.css('height', (curDiv.height() / rows) + "px");
				$this.cards[total].setDiv(cur);
				$this.cards[total].drawGivenSpace(cur);
				total++;
			}
		}
	}
	
	draw(){
		this.drawInDiv(this.div);
	}
	
	forceDrawInDiv(div){
		this.changed = true;
		this.drawInDiv(div);
	}
	
	drawInDiv(div){
		if(this.changed){
			this.changed = false;
			div.empty();
			this.fillDiv(div);
			this.prepareToolTips(div);
			this.sizeCards(div);
            if(this.expandInto != null){
                this.div.append('<div class="expandButton"></div>');
                this.prepareForExpand();
            }
            if(!isReplayMode){
                this.prepareDraggables();
                this.prepareDroppables();
                this.prepareDroppableZone();
            }
		}
	}
	
	prepareDraggables(){
        // let $this = this;
		// this.div.find(".card").draggable({ 
			// revert: false, 
			// helper: function(){
				// return "<div class='targetCursor'></div>";
			// },
			// cursorAt: { bottom: $this.getX(), left: $this.getY()}
		// });
        this.prepareDraggablesManual();
	}
    
    
    
    prepareDraggablesManual(){
        let $this = this;
                
        this.div.mouseup(function(event){
            mouseSystem.mouseupDiv($this,event);
        });
        
        this.div.find(".card").mousedown(function(event){
            mouseSystem.mousedown($(this).attr("id"),event)
        });
        
        this.div.find(".card").mouseup(function(event){
            mouseSystem.mouseupCard($(this).attr("id"),event);
        });

    }
    
    
    getX(){
        this.count+=100;
        console.log(this.count);
        return this.count;
    }
	
    getY(){
       this.count+=100;
       console.log(this.count);
       return this.count;
    }
    
	prepareDroppables(){
		// this.div.find(".card").droppable({
			// drop: function( event, ui ) {
				// $( this )
				  // .addClass( "cardSelected" );
				  // server.cardTargeted(ui.draggable.attr("id"),$(this).attr("id"));
			// },
			// greedy:true
		// })
	}
	
	prepareDroppableZone(){
		// this.div.droppable({
			// drop: function( event, ui ) {
				  // server.cardPlayed(ui.draggable.attr("id"),$(this));
			// }
		// });
	}
	
	sizeCards(div){
		div.children().children('.cardBox').css({
			'width':function(index, value){
				return $(this).height() * WIDTH_RATIO;
			}
		});
	}

	
	prepareToolTips(div){
		let maxHeight = $(document).height();
		let maxWidth = $(document).width();
		let height;
		if(maxHeight > 400){
			height = 350;
		}
		else{
			height = maxHeight * .8;
			$(".bigCardBox").css({
				fontSize : "10px",
				lineHeight : "90%"
			});
		}
		let width = height * WIDTH_RATIO;
		while(width > maxWidth){
			height -= 10;
			width = height * WIDTH_RATIO;
		}
		div.find('.hasTooltip').each(function() {
			$(this).qtip({
				content: {
					text: $(this).next('div') 
				},
                prerender: true,
				style: {
					height: height,
					width: width,
					classes: 'qtip-bootstrap',
				},
				position: { 
					viewport: $(window),
					adjust: {
						method: 'flipinvert shift'
					}
					
				}
			});
		});
	}
}