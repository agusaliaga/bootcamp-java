insert  into `atmospheres`(`id_atmosphere`,`humidity`,`pressure`,`rising`,`visibility`) values (1,80,1028,0,16.1),(2,50,1000.1,0,11.5),(3,65,1204.3,0,8.5),(4,55,1028.2,0,8.5);

insert  into `week_days`(`id_week_day`,`description`,`alpha3_code`) values (1,'Sunday','SUN'),(2,'Monday','MON'),(3,'Tuesday','TUE'),(4,'Wednesday','WED'),(5,'Thursday','THU'),(6,'Friday','FRI'),(7,'Saturday','SAT');

insert  into `winds`(`id_wind`,`speed`,`direction`) values (1,113,13),(2,222,12),(3,200,7),(4,25,10),(5,220,10),(6,125,12),(7,115,7),(8,100,8),(9,215,15),(10,213,13),(11,222,22),(12,222,11),(13,111,11),(14,122,22),(15,100,10),(16,13,13),(17,155,15),(18,155,15),(19,111,11),(20,11,6),(21,222,25),(22,12,6),(23,133,3),(24,133,33),(25,10,0),(26,10,6),(27,300,12);

insert  into `weather_descriptions`(`id_description`,`text`) values (1,'Sunny'),(2,'Partly Cloudy'),(3,'Mostly Cloudy'),(4,'Cloudy'),(5,'Storms'),(6,'Snow Showers'),(7,'Blowing Snow'),(8,'Snow');

insert  into `countries`(`id_country`,`full_name`,`alpha2_code`,`alpha3_code`) values (1,'India','IN','IND'),(2,'United States of America','US','USA'),(3,'Argentina','AR','ARG');

insert  into `states`(`id_state`,`id_country`,`full_name`,`alpha2_code`,`area`,`largest_city`,`capital_city`) values (1,1,'Andhra Pradesh','AP','49506799','Hyderabad Amaravati','Hyderabad Amaravati'),(2,1,'Arunachal Pradesh','AR','1383727','Itanagar','Itanagar'),(3,1,'Assam','AS','31205576','Dispur','Dispur'),(4,1,'Bihar','BR','104099452','Patna','Patna'),(5,2,'Alabama','AL','135767','Birmingham','Montgomery'),(6,2,'Alaska','AK','1723337','Anchorage','Juneau'),(7,2,'Arizona','AZ','113594','Phoenix','Phoenix'),(8,2,'Arkansas','AR','52035','Little Rock','Little Rock'),(9,2,'California','CA','423967','Los Angeles','Sacramento'),(10,3,'Cordoba','CO','45645','Cordoba','Cordoba');

insert  into `towns`(`id_town`,`id_state`,`full_name`) values (1,1,'Hyderabad Amaravati'),(2,2,'Itanagar'),(3,3,'Dispur'),(4,4,'Patna'),(5,10,'Villa Allende'),(6,10,'Agua de Oro');

insert  into weathers (id_weather,id_town,id_atmosphere,id_wind,temp_now,temp_max,temp_min,date_day,id_week_day,id_description) values 
(1,2,1,1,10,15,5,'2017-10-24 00:08:48',2,4),
(2,5,2,1,38,39,22,'2017-11-25 13:29:18',7,1),
(3,5,2,1,38,39,22,'2017-12-01 13:29:18',6,1),
(4,5,2,1,38,39,22,'2017-12-02 13:30:37',7,1),
(6,5,2,1,38,39,22,'2017-12-03 13:31:08',1,1),
(7,5,2,1,38,39,22,'2017-12-04 00:00:00',2,1),
(8,5,2,1,38,39,22,'2017-12-04 10:01:45',2,1),
(9,5,2,1,38,39,22,'2017-12-05 00:00:00',3,1),
(10,5,1,1,38,39,22,'2017-12-06 00:00:00',4,1),
(11,5,2,1,38,39,22,'2017-12-07 00:00:00',5,1),
(12,5,2,3,27,29,18,'2017-12-08 16:53:43',6,2),
(13,6,1,1,0,29,17,'2017-12-04 00:00:00',2,1),






