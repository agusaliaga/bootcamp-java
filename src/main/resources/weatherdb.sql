
CREATE TABLE winds (
  id_wind int(11) NOT NULL AUTO_INCREMENT,
  speed int(11) DEFAULT NULL,
  direction int(11) DEFAULT NULL,
  PRIMARY KEY (id_wind)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;


CREATE TABLE atmospheres (
  id_atmosphere int(11) NOT NULL AUTO_INCREMENT,
  humidity int(11) DEFAULT NULL,
  pressure decimal(10,1) DEFAULT NULL,
  rising int(11) DEFAULT NULL,
  visibility decimal(10,1) DEFAULT NULL,
  PRIMARY KEY (id_atmosphere)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


CREATE TABLE week_days (
  id_week_day int(11) NOT NULL AUTO_INCREMENT,
  description varchar(10) DEFAULT NULL,
  alpha3_code varchar(3) DEFAULT NULL,
  PRIMARY KEY (id_week_day)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

CREATE TABLE countries (
  id_country int(11) NOT NULL AUTO_INCREMENT,
  full_name varchar(30) DEFAULT NULL,
  alpha2_code varchar(2) DEFAULT NULL,
  alpha3_code varchar(3) DEFAULT NULL,
  PRIMARY KEY (id_country)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE states (
  id_state int(11) NOT NULL AUTO_INCREMENT,
  id_country int(11) NOT NULL,
  full_name varchar(60) DEFAULT NULL,
  alpha2_code varchar(2) DEFAULT NULL,
  area varchar(30) DEFAULT NULL,
  largest_city varchar(60) DEFAULT NULL,
  capital_city varchar(60) DEFAULT NULL,
  PRIMARY KEY (id_state),
  FOREIGN KEY (id_country) REFERENCES countries (id_country)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

CREATE TABLE towns (
  id_town int(11) NOT NULL AUTO_INCREMENT,
  id_state int(11) NOT NULL,
  full_name varchar(60) DEFAULT NULL,
  PRIMARY KEY (id_town),
  FOREIGN KEY (id_state) REFERENCES states (id_state)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE weather_descriptions (
  id_description int(11) NOT NULL AUTO_INCREMENT,
  text varchar(30) DEFAULT NULL,
  PRIMARY KEY (id_description)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

CREATE TABLE weathers (
  id_weather int(11) NOT NULL AUTO_INCREMENT,
  id_town int(11) NOT NULL,
  id_atmosphere int(11) DEFAULT NULL,
  id_wind int(11) DEFAULT NULL,
  temp_now int(11) DEFAULT NULL,
  temp_max int(11) DEFAULT NULL,
  temp_min int(11) DEFAULT NULL,
  date_day datetime NOT NULL,
  id_week_day int(11) DEFAULT NULL,
  id_description int(11) DEFAULT NULL,
  PRIMARY KEY (id_weather),
  FOREIGN KEY (id_town) REFERENCES towns (id_town),
  FOREIGN KEY (id_atmosphere) REFERENCES atmospheres (id_atmosphere),
  FOREIGN KEY (id_wind) REFERENCES winds (id_wind),
  FOREIGN KEY (id_week_day) REFERENCES week_days (id_week_day),
  FOREIGN KEY (id_description) REFERENCES weather_descriptions (id_description)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;




