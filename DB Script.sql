-- Creación de la base de datos Asesorias
CREATE SCHEMA `Asesorias` ;
USE Asesorias;
-- Tabla Rol
CREATE TABLE `Asesorias`.`Rol` (
  `id` TINYINT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(32) NOT NULL,
  `descripcion` VARCHAR(128) NULL,
  `habilitado` BIT NOT NULL,
  PRIMARY KEY (`id`));

-- Tabla Usuario
CREATE TABLE `Asesorias`.`Usuario` (
  `username` VARCHAR(32) NOT NULL,
  `nombre` VARCHAR(32) NOT NULL,
  `apellidos` VARCHAR(64) NULL,
  `email` VARCHAR(128) NULL,
  `password` VARCHAR(64) NOT NULL,
  `habilitado` BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (`username`));

-- Tabla Usuario_Rol
CREATE TABLE `Asesorias`.`Usuario_Rol` (
  `usuario` VARCHAR(32) NOT NULL,
  `rol` TINYINT NOT NULL,
  PRIMARY KEY (`usuario`, `rol`),
  INDEX `UsuarioRol_Ref_Rol_idx` (`rol` ASC),
  CONSTRAINT `UsuarioRol_Ref_Usuario`
    FOREIGN KEY (`usuario`)
    REFERENCES `Asesorias`.`Usuario` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `UsuarioRol_Ref_Rol`
    FOREIGN KEY (`rol`)
    REFERENCES `Asesorias`.`Rol` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- Tabla Piso
CREATE TABLE `asesorias`.`Piso` (
  `id` TINYINT(2) NOT NULL,
  `descripcion` VARCHAR(128) NOT NULL,
  `habilitado` BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`));
  
-- Tabla TipoSolicitante
CREATE TABLE `Asesorias`.`TipoSolicitante` (
  `nombre` VARCHAR(16) NOT NULL,
  `descripcion` VARCHAR(128) NOT NULL,
  `habilitado` BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (`nombre`));
  
-- Tabla Solicitante
  CREATE TABLE `Asesorias`.`Solicitante` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(32) NOT NULL,
  `apellidos` VARCHAR(64) NOT NULL,
  `tipo` VARCHAR(16) NOT NULL,
  `contacto` VARCHAR(128) NOT NULL,
  `habilitado` BIT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  INDEX `Solicitante_Ref_TipoSolicitante_idx` (`tipo` ASC) ,
  CONSTRAINT `Solicitante_Ref_TipoSolicitante`
    FOREIGN KEY (`tipo`)
    REFERENCES `asesorias`.`tiposolicitante` (`nombre`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- Tabla Asesoria
CREATE TABLE `Asesorias`.`Asesoria` (
  `id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `fecha` DATETIME NOT NULL,
  `solicitante` INT NOT NULL,
  `piso` TINYINT(2) NOT NULL,
  `observaciones` VARCHAR(256) NULL,
  PRIMARY KEY (`id`),
  INDEX `Asesoria_Ref_Piso_idx` (`piso` ASC),
  INDEX `Asesoria_Ref_Solicitante_idx` (`solicitante` ASC),
  CONSTRAINT `Asesoria_Ref_Piso`
    FOREIGN KEY (`piso`)
    REFERENCES `asesorias`.`piso` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Asesoria_Ref_Solicitante`
    FOREIGN KEY (`solicitante`)
    REFERENCES `asesorias`.`solicitante` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
-- Tabla TipoAsesoria
CREATE TABLE `Asesorias`.`TipoAsesoria` (
  `id` tinyint(3) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(32) NOT NULL,
  `descripcion` varchar(128) DEFAULT NULL,
  `habilitado` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`)
);

  
-- Tabla SubtipoAsesoria
CREATE TABLE `Asesorias`.`SubtipoAsesoria` (
  `id` tinyint(3) NOT NULL AUTO_INCREMENT,
  `tipo` tinyint(3) NOT NULL,
  `nombre` varchar(32) NOT NULL,
  `descripcion` varchar(256) NOT NULL,
  `habilitado` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `Subtipo_Ref_Tipo_idx` (`tipo`),
  CONSTRAINT `Subtipo_Ref_Tipo` FOREIGN KEY (`tipo`) REFERENCES `tipoasesoria` (`id`)
);

	
-- Tabla Actividad
CREATE TABLE `Asesorias`.`Actividad` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `asesoria` BIGINT(10) NOT NULL,
  `fecha` DATETIME NOT NULL,
  `estado` VARCHAR(16) NOT NULL,
  `tecnico` VARCHAR(32) NULL,
  `subtipo` TINYINT(3) NOT NULL,
  `observaciones` VARCHAR(256) NULL,
  PRIMARY KEY (`id`),
  INDEX `Actividad_Ref_Asesoria_idx` (`asesoria` ASC),
  INDEX `Actividad_Ref_Usuario_idx` (`tecnico` ASC),
  INDEX `Actividad_Ref_SubtipoAsesoria_idx` (`subtipo` ASC),
  CONSTRAINT `Actividad_Ref_Asesoria`
    FOREIGN KEY (`asesoria`)
    REFERENCES `asesorias`.`asesoria` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Actividad_Ref_Usuario`
    FOREIGN KEY (`tecnico`)
    REFERENCES `asesorias`.`usuario` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Actividad_Ref_SubtipoAsesoria`
    FOREIGN KEY (`subtipo`)
    REFERENCES `asesorias`.`subtipoasesoria` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

--Tabla UrlItem
CREATE TABLE `UrlItem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `valor` varchar(45) NOT NULL,
  `habilitado` bit(1) NOT NULL DEFAULT b'0',
  `etiqueta` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);
	

-- Procedimiento sp_DeleteRol
DROP procedure IF EXISTS `sp_deleteRol`;

DELIMITER $$
USE `Asesorias`$$
CREATE PROCEDURE `sp_deleteRol` (IN rol_id TINYINT)
BEGIN
	UPDATE Rol SET habilitado = 0 WHERE id = rol_id AND EXISTS(SELECT rol FROM Usuario_Rol WHERE rol = rol_id);
    SELECT * FROM Rol WHERE id = rol_id;
    DELETE FROM Rol WHERE id = rol_id AND NOT EXISTS (SELECT rol FROM Usuario_Rol WHERE rol = rol_id);
END$$

DELIMITER ;

-- Procedimiento sp_UpsertUsuario
USE `Asesorias`;
DROP procedure IF EXISTS `sp_UpsertUsuario`;

DELIMITER $$
USE `Asesorias`$$
CREATE PROCEDURE `sp_UpsertUsuario` (p_username VARCHAR(32), p_nombre VARCHAR(32), p_apellidos VARCHAR(64), p_password VARCHAR(64), p_email VARCHAR(64), p_habilitado BIT)
BEGIN
	INSERT INTO Usuario
		(username,nombre,apellidos,password,email,habilitado)
	VALUES
		(p_username,p_nombre,p_apellidos,p_password,p_email,p_habilitado)
	ON DUPLICATE KEY UPDATE
		nombre = p_nombre,
        apellidos = p_apellidos,
        password = p_password,
        email = p_email,
        habilitado = p_habilitado;
	SELECT * FROM Usuario WHERE username = p_username;
END$$

DELIMITER ;

-- Procedimiento sp_UpdateUsuarioRoles
USE `Asesorias`;
DROP procedure IF EXISTS `sp_UpdateUsuarioRoles`;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_UpdateUsuarioRoles`(IN p_username VARCHAR(32), IN p_roles VARCHAR(32))
BEGIN    
	SET @delete_sql = CONCAT('DELETE FROM Usuario_Rol WHERE usuario = \'', p_username, '\'', IF(p_roles IS NULL OR LENGTH(p_roles) = 0, '', CONCAT(' AND rol NOT IN(', p_roles ,')') ) );

	PREPARE delStmt FROM @delete_sql;
    EXECUTE delStmt;
	DEALLOCATE PREPARE delStmt;

	IF p_roles IS NOT NULL AND LENGTH(p_roles) > 0 THEN
		SET @insert_sql = 'INSERT IGNORE INTO Usuario_Rol (usuario,rol) VALUES ';
		
		WHILE LENGTH(p_roles) > 0 DO
			SET @rol = SUBSTRING_INDEX(p_roles,',',1);
			SET @insert_sql = CONCAT(@insert_sql, '(\'',p_username,'\',',@rol,'), ');
			SET p_roles = SUBSTR(p_roles, LENGTH(@rol) + 2, LENGTH(p_roles) - LENGTH(@rol) - 1);
		END WHILE;
		SET @insert_sql = SUBSTR(@insert_sql, 1, LENGTH(@insert_sql) - 2);
		
		PREPARE insStmt FROM @insert_sql;
		EXECUTE insStmt;
		DEALLOCATE PREPARE insStmt;
    END IF;
    
    SELECT R.* FROM Rol R JOIN Usuario_Rol UR ON(UR.rol = R.id) WHERE UR.usuario = p_username;
END$$
DELIMITER ;


-- Procedimiento sp_DeleteUsuario
USE `Asesorias`;
DROP procedure IF EXISTS `sp_DeleteUsuario`;

DELIMITER $$
USE `Asesorias`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_DeleteUsuario`(IN p_username VARCHAR(32))
BEGIN
	START TRANSACTION;
		DELETE FROM Usuario_Rol WHERE usuario = p_username;
        UPDATE Usuario SET habilitado = 0 WHERE username = p_username AND EXISTS(SELECT tecnico FROM Actividad WHERE tecnico = p_username);    
        SELECT * FROM Usuario WHERE p_username = p_username;
		DELETE FROM Usuario WHERE username = username AND NOT EXISTS (SELECT tecnico FROM Actividad WHERE tecnico = p_username);    
	COMMIT;
END$$

DELIMITER ;

-- Procedimiento sp_DeleteTipoSolicitante
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_DeleteTipoSolicitante`(IN tipo_nombre VARCHAR(16))
BEGIN
	UPDATE TipoSolicitante SET habilitado = 0 WHERE nombre = tipo_nombre AND EXISTS(SELECT * FROM Solicitante WHERE tipo = tipo_nombre);
    SELECT * FROM TipoSolicitante WHERE nombre = tipo_nombre;
    DELETE FROM TipoSolicitante WHERE nombre = tipo_nombre AND NOT EXISTS(SELECT * FROM Solicitante WHERE tipo = tipo_nombre);
END$$
DELIMITER ;

-- Procedimiento sp_DeletePiso
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_DeletePiso`(IN piso_id TINYINT)
BEGIN
	UPDATE Piso SET habilitado = 0 WHERE id = piso_id AND EXISTS(SELECT * FROM Asesoria WHERE piso = piso_id);
    SELECT * FROM Piso WHERE id = piso_id;
    DELETE FROM Piso WHERE id = piso_id AND NOT EXISTS(SELECT * FROM Asesoria WHERE piso = piso_id);
END$$
DELIMITER ;

-- Procedimiento sp_DeleteTipoAsesoria
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_DeleteTipoAsesoria`(IN tipo_id TINYINT)
BEGIN
	UPDATE TipoAsesoria SET habilitado = 0 WHERE id = tipo_id AND EXISTS(SELECT * FROM SubtipoAsesoria WHERE tipo = tipo_id);
    SELECT * FROM TipoAsesoria WHERE id = tipo_id;
    DELETE FROM TipoAsesoria WHERE id = tipo_id AND NOT EXISTS(SELECT * FROM SubtipoAsesoria WHERE tipo = tipo_id);
END$$
DELIMITER ;


-- Procedimiento sp_DeleteSubtipoAsesoria
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_DeleteSubtipoAsesoria`(IN subtipo_id TINYINT)
BEGIN
	UPDATE SubtipoAsesoria SET habilitado = 0 WHERE id = subtipo_id AND EXISTS(SELECT * FROM Actividad WHERE subtipo = subtipo_id);
    SELECT S.*,T.nombre tipo_nombre, T.descripcion tipo_descripcion, T.habilitado tipo_habilitado FROM SubtipoAsesoria S JOIN TipoAsesoria T ON(T.id = S.tipo) WHERE S.id = subtipo_id;
    DELETE FROM SubtipoAsesoria WHERE id = subtipo_id  AND NOT EXISTS(SELECT * FROM Actividad WHERE subtipo = subtipo_id);
END$$
DELIMITER ;


-- Procedimiento sp_DeleteSolicitante
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_DeleteSolicitante`(IN solicitante_id INT)
BEGIN
	UPDATE Solicitante SET habilitado = 0 WHERE id = solicitante_id AND EXISTS(SELECT * FROM Asesoria WHERE solicitante = solicitante_id);
    SELECT S.*, T.descripcion, T.habilitado tipo_habilitado FROM Solicitante S JOIN TipoSolicitante T ON(T.nombre = S.tipo) WHERE S.id = solicitante_id;
    DELETE FROM Solicitante WHERE id = solicitante_id AND NOT EXISTS(SELECT * FROM Asesoria WHERE solicitante = solicitante_id);
END$$
DELIMITER ;

-- Procedimiento sp_DeleteAsesoria
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_DeleteAsesoria`(IN asesoria_id BIGINT)
BEGIN
	DELETE FROM Actividad WHERE asesoria = asesoria_id;
    SELECT A.*, S.nombre solicitante_nombre, S.apellidos solicitante_apellidos, S.tipo solicitante_tipo, T.descripcion tipo_descripcion, T.habilitado tipo_habilitado, S.contacto solicitante_contacto, S.habilitado solicitante_habilitado, P.descripcion piso_descripcion, P.habilitado piso_habilitado FROM Asesoria A JOIN Solicitante S ON(S.id = A.solicitante) JOIN TipoSolicitante T ON(T.nombre = S.tipo) JOIN Piso P ON(P.id = A.piso) WHERE A.id = asesoria_id;
    DELETE FROM Asesoria WHERE id = asesoria_id;
END$$
DELIMITER ;

-- Procedimiento sp_DeleteActividad
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_DeleteActividad`(IN actividad_id BIGINT)
BEGIN
	SELECT A.*, U.*,S.tipo, S.nombre subtipo_nombre, S.descripcion subtipo_descripcion, S.habilitado subtipo_habilitado, T.nombre tipo_nombre, T.descripcion tipo_descripcion, T.habilitado tipo_habilitado FROM Actividad A JOIN SubtipoAsesoria S ON(S.id = A.subtipo) JOIN TipoAsesoria T ON(T.id = S.tipo) LEFT JOIN Usuario U ON(U.username = A.tecnico) WHERE A.id = actividad_id;
    DELETE FROM Actividad WHERE id = actividad_id;
END$$
DELIMITER ;

