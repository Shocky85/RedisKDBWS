# RedisKnowledgeDatabase

Routes :


 - GET http://<server>/rediskdb/links/{tags}

_Permet de récupérer la liste des liens correspondant à un ou plusieurs tags._
_Cette route retourne la liste des liens correspondant à un ou plusieurs tags._
_Dans le cas d'une recherche avec plusieurs tags, les liens retournés sont dans l'ordre décroissant des liens les plus plus présents dans l'ensemble des tags recherchés._

_{tags} prend la forme suivante : {tags} = ["tag1", tag2", ...]_
 
 - GET http://<server>/rediskdb/tags

_Permet de récupérer la liste des tags et le nombre de liens leur correspondant triés dans l'ordre décroissant._



 

