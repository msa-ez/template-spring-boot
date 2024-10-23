forEach: Aggregate
fileName: {{namePascalCase}}Repository.java
path: {{boundedContext.name}}/{{{options.packagePath}}}/domain
---
package {{options.package}}.domain;

{{#if boundedContext.readModels}} 
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
{{/if}}
import {{options.package}}.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel="{{namePlural}}", path="{{namePlural}}")
public interface {{namePascalCase}}Repository extends PagingAndSortingRepository<{{namePascalCase}}, {{aggregateRoot.keyFieldDescriptor.className}}>{
{{#attached 'View' this}}
{{#if queryParameters}}    
    @Query(value = "select {{../nameCamelCase}} " +
        "from {{../namePascalCase}} {{../nameCamelCase}} " +
        "where{{#checkParameterType queryParameters ../nameCamelCase }}{{/checkParameterType}}")
       {{#queryOption}}{{#if multipleResult}}{{#if useDefaultUri}}List<{{../../namePascalCase}}> {{../nameCamelCase}}{{else}}List<{{../../namePascalCase}}> {{#if apiPath}}{{apiPath}}{{else}}{{../nameCamelCase}}{{/if}}{{/if}}{{else}}{{#if useDefaultUri}}{{../../namePascalCase}} {{../nameCamelCase}}{{else}}{{../../namePascalCase}} {{#if apiPath}}{{apiPath}}{{else}}{{../nameCamelCase}}{{/if}}{{/if}}{{/if}}{{/queryOption}}
({{#checkParameter queryParameters}}{{className}} {{nameCamelCase}}{{^@last}}, {{/@last}}{{/checkParameter}}{{#queryOption}}{{#if multipleResult}}, Pageable pageable{{/if}}{{/queryOption}});
{{/if}}
{{/attached}}
}
<function>
 var me = this;
 me.contexts.views = []
  if(this.boundedContext.readModels)
  this.boundedContext.readModels.forEach(view=>{
      if(view.aggregate == me && view.dataProjection=="query-for-aggregate"){
          me.contexts.views.push(view);
      }
  })
  window.$HandleBars.registerHelper('isNotId', function (className) {
    return (className != 'id')
  })

  window.$HandleBars.registerHelper('isText', function (className) {
    return (className.endsWith("String"))
  })

  window.$HandleBars.registerHelper('isBoolean', function (className) {
    return (className.endsWith("Boolean"))
  })

  window.$HandleBars.registerHelper('isNumber', function (className) {
    return (className.endsWith("Long") || className.endsWith("Integer"))
  })

  window.$HandleBars.registerHelper('isDate', function (className) {
    return (className.endsWith("Date"))
  })
  window.$HandleBars.registerHelper('checkParameterType', function (parameter, aggName) {
    var query = ''
    for( var i = 0; i < parameter.length; i++){
      if( i < parameter[i].length){
        query = "and"
      }else{
        query = ''
      }
      if(parameter[i].className == 'String'){
        return `(:${parameter[i].nameCamelCase} is null or ${aggName}.${value} like %:${parameter[i].nameCamelCase}%)` + query
      }else if(parameter[i].className == 'Boolean'){
        return `(${aggName}.${parameter[i].nameCamelCase} = :${parameter[i].nameCamelCase})` + query
      }else if(parameter[i].className == 'Long' || parameter[i].className == 'Integer' || parameter[i].className == 'Double' || parameter[i].className == 'Float' || parameter[i].className == 'BigDecimal'){
        return `(:${parameter[i].nameCamelCase} is null or ${aggName}.${parameter[i].nameCamelCase} = :${parameter[i].nameCamelCase})` + query
      }else if(parameter[i].className == parameter[i].namePascalCase && !parameter[i].isVO){
        return `(${aggName}.${parameter[i].nameCamelCase} = :${parameter[i].nameCamelCase})` + query
      }else{
        return
      }
    }
  })

  window.$HandleBars.registerHelper('checkParameter', function (parameter, options) {
    var query = ''
    if(parameter.className == 'String' || parameter.className == 'Boolean' || parameter.className == 'Long' || parameter.className == 'Integer' || parameter.className == 'Double' || parameter.className == parameter.namePascalCase){
      query = parameter.className + " " + parameter.nameCamelCase
      return query;
    }else{
      return options.inverse(this);
    }
  })

  window.$HandleBars.registerHelper('toURL', function (className) {

    var pluralize = function(value, revert){

      var plural = {
          '(quiz)$'               : "$1zes",
          '^(ox)$'                : "$1en",
          '([m|l])ouse$'          : "$1ice",
          '(matr|vert|ind)ix|ex$' : "$1ices",
          '(x|ch|ss|sh)$'         : "$1es",
          '([^aeiouy]|qu)y$'      : "$1ies",
          '(hive)$'               : "$1s",
          '(?:([^f])fe|([lr])f)$' : "$1$2ves",
          '(shea|lea|loa|thie)f$' : "$1ves",
          'sis$'                  : "ses",
          '([ti])um$'             : "$1a",
          '(tomat|potat|ech|her|vet)o$': "$1oes",
          '(bu)s$'                : "$1ses",
          '(alias)$'              : "$1es",
          '(octop)us$'            : "$1i",
          '(ax|test)is$'          : "$1es",
          '(us)$'                 : "$1es",
          '([^s]+)$'              : "$1s"
      };

      var singular = {
          '(quiz)zes$'             : "$1",
          '(matr)ices$'            : "$1ix",
          '(vert|ind)ices$'        : "$1ex",
          '^(ox)en$'               : "$1",
          '(alias)es$'             : "$1",
          '(octop|vir)i$'          : "$1us",
          '(cris|ax|test)es$'      : "$1is",
          '(shoe)s$'               : "$1",
          '(o)es$'                 : "$1",
          '(bus)es$'               : "$1",
          '([m|l])ice$'            : "$1ouse",
          '(x|ch|ss|sh)es$'        : "$1",
          '(m)ovies$'              : "$1ovie",
          '(s)eries$'              : "$1eries",
          '([^aeiouy]|qu)ies$'     : "$1y",
          '([lr])ves$'             : "$1f",
          '(tive)s$'               : "$1",
          '(hive)s$'               : "$1",
          '(li|wi|kni)ves$'        : "$1fe",
          '(shea|loa|lea|thie)ves$': "$1f",
          '(^analy)ses$'           : "$1sis",
          '((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$': "$1$2sis",
          '([ti])a$'               : "$1um",
          '(n)ews$'                : "$1ews",
          '(h|bl)ouses$'           : "$1ouse",
          '(corpse)s$'             : "$1",
          '(us)es$'                : "$1",
          's$'                     : ""
      };

      var irregular = {
          'move'   : 'moves',
          'foot'   : 'feet',
          'goose'  : 'geese',
          'sex'    : 'sexes',
          'child'  : 'children',
          'man'    : 'men',
          'tooth'  : 'teeth',
          'person' : 'people',
          'index'  : 'indexes'
      };

      var uncountable = [
          'sheep',
          'fish',
          'deer',
          'moose',
          'series',
          'species',
          'money',
          'rice',
          'information',
          'equipment'
      ];

      // save some time in the case that singular and plural are the same
//      console.log("value = " + value)
      if(uncountable.indexOf(value.toLowerCase()) >= 0)
      return this;

      // check for irregular forms
      for(var word in irregular){

          if(revert){
                  var pattern = new RegExp(irregular[word]+'$', 'i');
                  var replace = word;
          } else{ var pattern = new RegExp(word+'$', 'i');
                  var replace = irregular[word];
          }
          if(pattern.test(value))
              return value.replace(pattern, replace);
      }

      if(revert) var array = singular;
          else  var array = plural;

      // check for matches using regular expressions
      for(var reg in array){

          var pattern = new RegExp(reg, 'i');

          if(pattern.test(value))
              return value.replace(pattern, array[reg]);
      }

      return value;
    }

    return pluralize(className.toLowerCase())
  })
  window.$HandleBars.registerHelper('changeUpper', function (name) {
    return name.charAt(0).toUpperCase() + name.slice(1);
  });

</function>
