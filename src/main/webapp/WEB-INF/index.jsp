<!DOCTYPE html>
<html>
<body>
<h2>Welcome to Weather App!</h2>

<h3>Weather</h3>
	 <ul>
          <li> <b>1) POST weather for TODAY manually with JSON </b> example: /weather/new/today/ </li> 
	 	  <li> <b>2) POST weather FORECAST by Town using JSON</b> example: /weather/new/forecast/ </li> 
	 	  <li> <b>3) GET weather TODAY for a town, state, country (LIVE) </b> example: /weather/get/today/town/?town=Phoenix&state=AZ&country=USA </li>
	 	  <li> <b>4) GET weather FORECAST for a town, state, country (LIVE) </b> example: /weather/get/forecast/town/?town=Nome&state=AK&country=USA</li> 
	 	  <li> <b>5) GET last inserted weather register from DB </b> example: /weather/get/last/</li>
	 	  <li> <b>6) GET TODAY's weather for all the cities of a state </b> example: weather/get/today/state/?country=USA&state=AK </li>  
	 	  <li> <b>7) PUT weather update by weather ID with JSON</b> example: /weather/update/</li>   	  
	  </ul>
	  
	  
<h3>Countries</h3>
 	<ul>
          <li> <b>1) GET all countries by short code </b> example: /country/get/all/</li> 
	 	  <li> <b>2) GET one country by short code (LIVE)</b> example: /country/get/2code/?2code=NZ </li> 
	 	  <li> <b>3) GET country by long code (LIVE) </b> example: /country/get/3code/?3code=DZA </li>
	 	  <li> <b>4) POST country with JSON manually</b> example: /country/get/new/ </li>  	  
	</ul> 
	
<h3>States</h3>
 	<ul>
          <li> <b>1) GET all states from one country </b> example: /state/get/all/?3code=IND</li> 
	 	  <li> <b>2) GET one state from one country (LIVE) </b> example: /state/get/one/?country=USA&state=TX</li> 
	 	  <li> <b>3) POST state with JSON manually </b> example: /state/new/ </li>	  
	</ul>  	
	 
</body>
</html>
