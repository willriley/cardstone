<#assign lobby_display>
<div class="col-md-4 tallCol">
  <div class="panel panel-default tallPanel" id="blah">
    <div class="panel-heading">
      <h3 class="panel-title">You</h3>
    </div>
    <div class="panel-body">
		<select class="form-control" id="deckselect">
		  <option selected>Pick a deck</option>
		  <#list decks as deck>
          	<option value="${deck}">${deck}</option>
    	  </#list>
		</select>
		<p id="message"> </p>
    </div>
  </div>
</div>
<div class="col-md-4 tallCol">
	<h4>Versus</h4>
</div>
<div class="col-md-4 tallCol pull-right">
  <div class="panel panel-default tallPanel">
    <div class="panel-heading">
      <h3 class="panel-title" id="oppname">${opp}</h3>
    </div>
    <div class="panel-body">
	    <p id="oppmessage">${oppMsg} </p>
    </div>
  </div>
</div>
</#assign>