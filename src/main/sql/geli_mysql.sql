
CREATE TABLE gl_keygen (
  table_name varchar(100) NOT NULL,
  last_used_id int(10) unsigned NOT NULL,
  PRIMARY KEY (table_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE gl_user (
  userId int(10) unsigned NOT NULL,
  account varchar(45) DEFAULT NULL,
  name varchar(45) NOT NULL,
  createAt datetime DEFAULT NULL,
  loginAt datetime DEFAULT NULL,
  PRIMARY KEY (userId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE gl_function (
  functionId varchar(255) NOT NULL,
  name varchar(255) DEFAULT NULL,
  PRIMARY KEY (functionId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE gl_role (
  roleId int(10) unsigned NOT NULL,
  name varchar(255) DEFAULT NULL,
  PRIMARY KEY (roleId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE gl_role_function (
  id int(10) unsigned NOT NULL,
  roleId int(10) unsigned NOT NULL,
  functionId varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE gl_acl (
  aclId int(10) unsigned NOT NULL,
  userId int(10) unsigned DEFAULT NULL,
  resourceId varchar(255) DEFAULT NULL,
  roleId int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (aclId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE gl_session (
  sessionId varchar(255) NOT NULL,
  userId int(10) unsigned DEFAULT NULL,
  createAt datetime DEFAULT NULL,
  PRIMARY KEY (sessionId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE gl_log (
  logId int(10) unsigned NOT NULL,
  userId int(10) unsigned DEFAULT NULL,
  ip varchar(255) DEFAULT NULL,
  logAt datetime DEFAULT NULL,
  uri varchar(255) DEFAULT NULL,
  query_ varchar(255) DEFAULT NULL,
  PRIMARY KEY (logId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE gl_log_detail (
  logDetailId int(10) unsigned NOT NULL,
  logId int(10) unsigned DEFAULT NULL,
  type_ varchar(255) DEFAULT NULL,
  before_ text,
  after_ text,
  PRIMARY KEY (logDetailId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
