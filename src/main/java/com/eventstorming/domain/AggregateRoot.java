forEach: Aggregate
representativeFor: Aggregate
fileName: {{namePascalCase}}.java
path: {{boundedContext.name}}/{{options.packagePath}}/domain
---
package {{options.package}}.domain;

{{#lifeCycles}}
{{#events}}
import {{../../options.package}}.domain.{{namePascalCase}};
{{/events}}
{{/lifeCycles}}
import {{options.package}}.{{boundedContext.namePascalCase}}Application;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;
import java.time.LocalDate;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
{{#checkBigDecimal aggregateRoot.fieldDescriptors}}{{/checkBigDecimal}}

@Entity
@Table(name="{{namePascalCase}}_table")
@Data
{{#setDiscriminator aggregateRoot.entities.relations nameCamelCase}}{{/setDiscriminator}}
//<<< DDD / Aggregate Root
public class {{namePascalCase}} {{#checkExtends aggregateRoot.entities.relations namePascalCase}}{{/checkExtends}} {

    {{#aggregateRoot.fieldDescriptors}}
    {{^isVO}}
    {{#isKey}}
    @Id
    {{#checkClassType ../aggregateRoot.fieldDescriptors}}{{/checkClassType}}
    {{/isKey}}
    {{/isVO}}
    {{#isLob}}@Lob{{/isLob}}
    {{#if (isPrimitive className)}}{{#isList}}{{/isList}}{{/if}}
    {{#checkFieldType className isVO namePascalCase isKey ../aggregateRoot.entities.relations}}{{/checkFieldType}}
    {{#checkEntityField className nameCamelCase isVO label}}
    {{/checkEntityField}}
    {{/aggregateRoot.fieldDescriptors}}

    {{#commands}}
    {{#if isRestRepository}}
    {{else}}
    {{#isEntity ../aggregateRoot.entities.relations}}
    {{#../aggregateRoot.entities.relations}}
    {{#if targetElement}}
    {{#if targetElement.isVO}}
    {{else}}
    {{#isEnum targetElement._type}}

    public void add{{targetElement.namePascalCase}}({{#targetElement.fieldDescriptors}}{{#if isKey}}{{else}}{{className}} {{nameCamelCase}}{{^@last}}, {{/@last}}{{/if}}{{/targetElement.fieldDescriptors}}) {
        {{targetElement.namePascalCase}} {{targetElement.nameCamelCase}} = new {{targetElement.namePascalCase}}({{#targetElement.fieldDescriptors}}{{#if isKey}}{{else}}{{nameCamelCase}}{{^@last}}, {{/@last}}{{/if}}{{/targetElement.fieldDescriptors}}, this);
        {{#changeLower name}}{{/changeLower}}.add({{targetElement.nameCamelCase}});
    }

    public List<{{targetElement.namePascalCase}}> get{{#changeUpper name}}{{/changeUpper}}() {
        return Collections.unmodifiableList({{#changeLower name}}{{/changeLower}});
    }

    public void update{{targetElement.namePascalCase}}({{#targetElement.fieldDescriptors}}{{className}} {{nameCamelCase}}{{^@last}}, {{/@last}}{{/targetElement.fieldDescriptors}}) {
        for ({{targetElement.namePascalCase}} item : {{#changeLower name}}{{/changeLower}}) {
            if (item.{{#targetElement.fieldDescriptors}}{{#if isKey}}get{{namePascalCase}}().equals({{nameCamelCase}}){{/if}}{{/targetElement.fieldDescriptors}}) {
                item.update({{#targetElement.fieldDescriptors}}{{#if isKey}}{{else}}{{nameCamelCase}}{{^@last}}, {{/@last}}{{/if}}{{/targetElement.fieldDescriptors}});
                break;
            }
        }
    }

    public void remove{{targetElement.namePascalCase}}({{targetElement.namePascalCase}} {{targetElement.nameCamelCase}}) {
        {{#changeLower name}}{{/changeLower}}.remove({{targetElement.nameCamelCase}});
    }
    {{/isEnum}}
    {{/if}}
    {{else}}
    {{/if}}
    {{/../aggregateRoot.entities.relations}}
    {{/isEntity}}
    {{/if}}
    {{/commands}}

{{#lifeCycles}}
    {{#isNotRelatedPolicy events}}
    {{annotation}}
    public void on{{trigger}}(){
    {{#commands}}
    {{#if isRestRepository}}
    {{#relationCommandInfo}}
    {{#checkEqualBoundedContext commandValue targetAggregate}}
    {{#if targetAggregate}}
    {{#targetAggregate}}
    {{#isReadModel this}}
    {{#if queryOption.multipleResult}}
    {{#if queryOption.useDefaultUri}}
    List<{{aggregate.namePascalCase}}> {{aggregate.nameCamelCase}}List = {{../../../../namePascalCase}}Application.applicationContext
        .getBean({{../../../../options.package}}.external.{{aggregate.namePascalCase}}Service.class)
        .{{nameCamelCase}}//({{#queryParameters}}get{{namePascalCase}}(){{#unless @last}},{{/unless}}{{/queryParameters}});
    {{else}}
    {{../../../../options.package}}.external.{{namePascalCase}}Query {{nameCamelCase}}Query = new {{../../../../options.package}}.external.{{namePascalCase}}Query();
    // {{nameCamelCase}}Query.set??()
    List<{{aggregate.namePascalCase}}> {{aggregate.nameCamelCase}}List = {{../../../../namePascalCase}}Application.applicationContext
        .getBean({{../../../../options.package}}.external.{{aggregate.namePascalCase}}Service.class)
        .{{#if queryOption.apiPath}}{{queryOption.apiPath}}{{else}}{{nameCamelCase}}{{/if}}({{nameCamelCase}}Query);
    {{/if}}
    {{else}}
    {{#if queryOption.useDefaultUri}}
    {{aggregate.namePascalCase}} {{aggregate.nameCamelCase}} = {{../../../../namePascalCase}}Application.applicationContext
        .getBean({{../../../../options.package}}.external.{{aggregate.namePascalCase}}Service.class)
        .{{nameCamelCase}}(get??);
    {{else}}
    {{../../../../options.package}}.external.{{namePascalCase}}Query {{nameCamelCase}}Query = new {{../../../../options.package}}.external.{{namePascalCase}}Query();
    // {{nameCamelCase}}Query.set??()        
    {{aggregate.namePascalCase}} {{aggregate.nameCamelCase}} = {{../../../../namePascalCase}}Application.applicationContext
        .getBean({{../../../../options.package}}.external.{{aggregate.namePascalCase}}Service.class)
        .{{#if queryOption.apiPath}}{{queryOption.apiPath}}{{else}}{{nameCamelCase}}{{/if}}({{nameCamelCase}}Query);
    {{/if}}
    {{/if}}
    {{/isReadModel}}
    {{/targetAggregate}}
    {{/if}}
    {{/checkEqualBoundedContext}}
    {{^checkEqualBoundedContext commandValue targetAggregate}}
    {{#if targetAggregate.queryOption.multipleResult}}
        {{targetAggregate.aggregate.namePascalCase}}Repository {{targetAggregate.aggregate.nameCamelCase}}Repository = {{targetAggregate.aggregate.namePascalCase}}.repository();
        
        {{targetAggregate.namePascalCase}}Query {{targetAggregate.nameCamelCase}}Query = new {{targetAggregate.namePascalCase}}Query();


        // Map the data and fields to be retrieved from the query. 
        // ex) getOrderItemQuery.setProductName(this.getProductName());
        {{#targetAggregate.queryParameters}}
        {{../targetAggregate.nameCamelCase}}Query.set{{namePascalCase}}(this.get??);
        {{/targetAggregate.queryParameters}}

        List<{{targetAggregate.aggregate.namePascalCase}}> search{{targetAggregate.aggregate.namePascalCase}} = {{targetAggregate.aggregate.nameCamelCase}}Repository.{{targetAggregate.nameCamelCase}}({{targetAggregate.nameCamelCase}}Query);
        
        if(search{{targetAggregate.namePascalCase}}.isEmpty()){
            throw new IllegalArgumentException("No data found for search: " + {{#targetAggregate.queryParameters}}search{{../targetAggregate.namePascalCase}}.get{{namePascalCase}}(){{^@last}}, {{/@last}}{{/targetAggregate.queryParameters}});
        }
    {{/if}} 
    {{/checkEqualBoundedContext}}
    {{/relationCommandInfo}}
    {{/if}}
    {{/commands}}
    {{#events}}

        {{#relationCommandInfo}}
            {{#commandValue}}
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        {{^isRestRepository}}
        {{#if (has fieldDescriptors)}}
        {{../../../../options.package}}.external.{{namePascalCase}}Command {{nameCamelCase}}Command = new {{../../../../options.package}}.external.{{namePascalCase}}Command();
        // mappings goes here
        {{../boundedContext.namePascalCase}}Application.applicationContext.getBean({{../../../../options.package}}.external.{{aggregate.namePascalCase}}Service.class)
            .{{nameCamelCase}}(/* get???(), */ {{nameCamelCase}}Command);
        {{/if}}
        {{/isRestRepository}}

        {{#isRestRepository}}
        {{../../../../options.package}}.external.{{aggregate.namePascalCase}} {{aggregate.nameCamelCase}} = new {{../../../../options.package}}.external.{{aggregate.namePascalCase}}();
        // mappings goes here
        {{../boundedContext.namePascalCase}}Application.applicationContext.getBean({{../../../../options.package}}.external.{{aggregate.namePascalCase}}Service.class)
            .{{nameCamelCase}}({{aggregate.nameCamelCase}});
        {{/isRestRepository}}

            {{/commandValue}}
        {{/relationCommandInfo}}

        {{namePascalCase}} {{nameCamelCase}} = new {{namePascalCase}}(this);
        {{nameCamelCase}}.publishAfterCommit();

    {{/events}}
    
    }
{{/isNotRelatedPolicy}}
{{/lifeCycles}}

    public static {{namePascalCase}}Repository repository(){
        {{namePascalCase}}Repository {{nameCamelCase}}Repository = {{boundedContext.namePascalCase}}Application.applicationContext.getBean({{namePascalCase}}Repository.class);
        return {{nameCamelCase}}Repository;
    }

{{#aggregateRoot.operations}}
    {{#setOperations ../commands name}}
    {{#isOverride}}
    @Override
    {{/isOverride}}
    {{^isRootMethod}}
    public {{returnType}} {{name}}(){
        //
    }
    {{/isRootMethod}}
    {{/setOperations}}
{{/aggregateRoot.operations}}


    {{#commands}}
    {{#if isRestRepository}}
    {{else}}
//<<< Clean Arch / Port Method
    public void {{nameCamelCase}}({{#if (has fieldDescriptors)}}{{namePascalCase}}Command {{nameCamelCase}}Command{{/if}}){
        
        //implement business logic here:
        
        {{#triggerByCommand}}
        {{#relationCommandInfo}}
        {{#commandValue}}
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        {{../../../../options.package}}.external.{{aggregate.namePascalCase}} {{aggregate.nameCamelCase}} = new {{../../../../options.package}}.external.{{aggregate.namePascalCase}}();
        // mappings goes here
        {{../boundedContext.namePascalCase}}Application.applicationContext.getBean({{../../../../options.package}}.external.{{aggregate.namePascalCase}}Service.class)
            .{{nameCamelCase}}({{aggregate.nameCamelCase}});

        {{/commandValue}}
        {{/relationCommandInfo}}
        {{/triggerByCommand}}

        {{#relationCommandInfo}}
        {{#if targetAggregate}}
        {{#targetAggregate}}
        {{#if queryOption.multipleResult}}
        {{#if queryOption.useDefaultUri}}
        List<{{aggregate.namePascalCase}}> {{aggregate.nameCamelCase}}List = {{../../../namePascalCase}}Application.applicationContext
            .getBean({{../../../options.package}}.external.{{aggregate.namePascalCase}}Service.class)
            .{{nameCamelCase}}//({{#queryParameters}}get{{namePascalCase}}(){{#unless @last}},{{/unless}}{{/queryParameters}});
        {{else}}
        {{../../../options.package}}.external.{{namePascalCase}}Query {{nameCamelCase}}Query = new {{../../../options.package}}.external.{{namePascalCase}}Query();
        // {{nameCamelCase}}Query.set??()
        List<{{aggregate.namePascalCase}}> {{aggregate.nameCamelCase}}List = {{../../../namePascalCase}}Application.applicationContext
            .getBean({{../../../options.package}}.external.{{aggregate.namePascalCase}}Service.class)
            .{{#if queryOption.apiPath}}{{queryOption.apiPath}}{{else}}{{nameCamelCase}}{{/if}}({{nameCamelCase}}Query);
        {{/if}}
        {{else}}
        {{#if queryOption.useDefaultUri}}
        {{aggregate.namePascalCase}} {{aggregate.nameCamelCase}} = {{../../../namePascalCase}}Application.applicationContext
            .getBean({{../../../options.package}}.external.{{aggregate.namePascalCase}}Service.class)
            .{{nameCamelCase}}(get??);
        {{else}}
        {{../../../options.package}}.external.{{namePascalCase}}Query {{nameCamelCase}}Query = new {{../../../options.package}}.external.{{namePascalCase}}Query();
        // {{nameCamelCase}}Query.set??()        
        {{aggregate.namePascalCase}} {{aggregate.nameCamelCase}} = {{../../../namePascalCase}}Application.applicationContext
            .getBean({{../../../options.package}}.external.{{aggregate.namePascalCase}}Service.class)
            .{{#if queryOption.apiPath}}{{queryOption.apiPath}}{{else}}{{nameCamelCase}}{{/if}}({{nameCamelCase}}Query);
        {{/if}}
        {{/if}}
        {{/targetAggregate}}
        {{/if}}
        {{/relationCommandInfo}}

        {{#triggerByCommand}}
        {{eventValue.namePascalCase}} {{eventValue.nameCamelCase}} = new {{eventValue.namePascalCase}}(this);
        {{#correlationGetSet .. eventValue}}
        {{#if target}}
        {{../eventValue.nameCamelCase}}.set{{target.namePascalCase}}({{../../nameCamelCase}}Command.get{{source.namePascalCase}}());
        {{/if}}
        {{/correlationGetSet}}
        {{eventValue.nameCamelCase}}.publishAfterCommit();
        {{/triggerByCommand}}
    }
//>>> Clean Arch / Port Method
    {{/if}}
    {{/commands}}

    {{#policyList}}
    {{#relationEventInfo}}
//<<< Clean Arch / Port Method
    public static void {{../nameCamelCase}}({{eventValue.namePascalCase}} {{eventValue.nameCamelCase}}){
        
        //implement business logic here:
        
        /** Example 1:  new item 
        {{../../namePascalCase}} {{../../nameCamelCase}} = new {{../../namePascalCase}}();
        repository().save({{../../nameCamelCase}});

        {{#../relationExampleEventInfo}}
        {{eventValue.namePascalCase}} {{eventValue.nameCamelCase}} = new {{eventValue.namePascalCase}}({{../../../nameCamelCase}});
        {{eventValue.nameCamelCase}}.publishAfterCommit();
        {{/../relationExampleEventInfo}}
        */

        /** Example 2:  finding and process
        
        {{#if eventValue.aggregate.outgoingRelations}}
        // if {{eventValue.nameCamelCase}}.{{#eventValue.aggregate.outgoingRelations}}{{#target}}{{nameCamelCase}}Id{{/target}}{{/eventValue.aggregate.outgoingRelations}} exists, use it
        
        // ObjectMapper mapper = new ObjectMapper();
        {{#eventValue.aggregate.outgoingRelations}}
        {{#target}}
        // Map<{{#aggregateRoot.fieldDescriptors}}{{#if isKey}}{{className}}{{/if}}{{/aggregateRoot.fieldDescriptors}}, Object> {{../../eventValue.aggregate.nameCamelCase}}Map = mapper.convertValue({{../../eventValue.nameCamelCase}}.get{{namePascalCase}}Id(), Map.class);
        {{/target}}
        {{/eventValue.aggregate.outgoingRelations}}
        {{/if}}

        repository().findById({{eventValue.nameCamelCase}}.get???()).ifPresent({{../../nameCamelCase}}->{
            
            {{../../nameCamelCase}} // do something
            repository().save({{../../nameCamelCase}});

            {{#../relationExampleEventInfo}}
            {{eventValue.namePascalCase}} {{eventValue.nameCamelCase}} = new {{eventValue.namePascalCase}}({{../../../nameCamelCase}});
            {{eventValue.nameCamelCase}}.publishAfterCommit();
            {{/../relationExampleEventInfo}}

         });
        */

        
    }
//>>> Clean Arch / Port Method
    {{/relationEventInfo}}
    {{/policyList}}


}
//>>> DDD / Aggregate Root

<function>
window.$HandleBars.registerHelper('isEnum', function (type, options) {
    if(type.includes("enum")){
        return options.inverse(this);
    }else{
        return options.fn(this);
    }
});
window.$HandleBars.registerHelper('checkEqualBoundedContext', function (source, target, options) {
    var sourceBC = '';
    var targetBC = '';
    sourceBC = source.boundedContext.name
    targetBC = target.boundedContext.name
    if(sourceBC == targetBC){
        return options.inverse(this);
    }else{
        return options.fn(this);
    }
});
window.$HandleBars.registerHelper('isNotRelatedPolicy', function (event, options) {
    if(event){
        for(var i = 0; i < event.length; i ++ ){
            if(event[i].incomingRelations){
                for(var j = 0; j < event[i].incomingRelations.length; j ++ ){
                    if(event[i].incomingRelations[j].source){
                        if(event[i].incomingRelations[j].source._type.endsWith("Policy")){
                            return options.inverse(this);
                            break;
                        }else{
                            return options.fn(this);
                            break;
                        }
                    }else{
                        return options.fn(this);
                    }
                }
            }else{
                return options.fn(this);
            }
        }
    }else{
        return options.inverse(this);
    }
});
window.$HandleBars.registerHelper('checkEntityField', function (type, name, isVO, label) {
    if(type.includes("List<") && !isVO && label){
        return "private" + " " + type + " " + name + " " + "= " + "new java.util.ArrayList<>();";
    }else{
        return "private" + " " + type + " " + name + ";";
    }
});
window.$HandleBars.registerHelper('changeUpper', function (name) {
    if (!name || typeof name !== 'string' || name.length === 0) {
        return '';
    }
    return name.charAt(0).toUpperCase() + name.slice(1);
});
window.$HandleBars.registerHelper('changeLower', function (name) {
    if (!name || typeof name !== 'string' || name.length === 0) {
        return '';
    }
    return name.charAt(0).toLowerCase() + name.slice(1);
});
window.$HandleBars.registerHelper('isEntity', function (relation, options) {
    var entityRelations = relation.filter(function(rel) {
        return rel !== null && 
               rel !== undefined && 
               rel.targetElement && 
               !rel.targetElement._type.endsWith('enum') && 
               !rel.targetElement.isVO;
    });
    
    if(entityRelations.length > 0) {
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
});

window.$HandleBars.registerHelper('checkClassType', function (fieldDescriptors) {
    for(var i = 0; i < fieldDescriptors.length; i ++ ){
        if(fieldDescriptors[i] && fieldDescriptors[i].className == 'Long'){
            return "@GeneratedValue(strategy=GenerationType.AUTO)";
        }
    }
    return "";
});

window.$HandleBars.registerHelper('checkDateType', function (fieldDescriptors) {
    for(var i = 0; i < fieldDescriptors.length; i ++ ){
        if(fieldDescriptors[i] && fieldDescriptors[i].className == 'Date'){
            return "import java.util.Date; \n"
        }
    }
});

window.$HandleBars.registerHelper('checkBigDecimal', function (fieldDescriptors) {
    for(var i = 0; i < fieldDescriptors.length; i ++ ){
        if(fieldDescriptors[i] && fieldDescriptors[i].className.includes('BigDecimal')){
            return "import java.math.BigDecimal;";
        }
    }
});

window.$HandleBars.registerHelper('isReadModel', function (sticker, options) {
    if(sticker._type.includes("View")){
        return options.fn(this)
    }else{
        return options.inverse(this)
    }
});

// window.$HandleBars.registerHelper('checkAttribute', function (relations, source, target, isVO) {
//    try {
//        if(typeof relations === "undefined"){
//         return;
//         }

//         if(!isVO){
//             return;
//         }

//         var sourceObj = [];
//         var targetObj = [];
//         var sourceTmp = {};
//         var targetName = null;
//         for(var i = 0 ; i<relations.length; i++){
//             if(relations[i] != null){
//                 if(relations[i].sourceElement.name == source){
//                     sourceTmp = relations[i].sourceElement;
//                     sourceObj = relations[i].sourceElement.fieldDescriptors;
//                 }
//                 if(relations[i].targetElement.name == target){
//                     targetObj = relations[i].targetElement.fieldDescriptors;
//                     targetName = relations[i].targetElement.nameCamelCase;
//                 }
//             }
//         }

//         var samePascal = [];
//         var sameCamel = [];
//         for(var i = 0; i<sourceObj.length; i++){
//             for(var j =0; j<targetObj.length; j++){
//                 if(sourceObj[i].name == targetObj[j].name){
//                     samePascal.push(sourceObj[i].namePascalCase);
//                     sameCamel.push(sourceObj[i].nameCamelCase);
//                 }
//             }
//         }

//         var attributeOverrides = "";
//         for(var i =0; i<samePascal.length; i++){
//             var camel = sameCamel[i];
//             var pascal = samePascal[i];
//             var overrides = `@AttributeOverride(name="${camel}", column= @Column(name="${targetName}${pascal}", nullable=true))\n`;
//             attributeOverrides += overrides;
//         }

//         return attributeOverrides;
//     } catch (e) {
//        console.log(e)
//     }


// });

window.$HandleBars.registerHelper('isPrimitive', function (className) {
    if(className.includes("String") || className.includes("Integer") || className.includes("Long") || className.includes("Double") || className.includes("Float")
            || className.includes("Boolean") || className.includes("Date")){
        return true;
    } else {
        return false;
    }
});

window.$HandleBars.registerHelper('checkFieldType', function (className, isVO, name, isKey, relation) {
    try {
        if (className === "Integer" || className === "String" || className === "Boolean" || className === "Float" || 
           className === "Double" || className === "Long" || className === "Date" || className === "BigDecimal") {
            return;
        }
        
        if (className.includes("List")) {
            const foundRelation = relation.find(rel => 
                rel && rel.name === name && !rel.targetElement._type.endsWith("enum"));
                
            if (foundRelation) {
                var aggName = foundRelation.sourceElement.name;
                aggName = aggName.charAt(0).toLowerCase() + aggName.slice(1);
                if (foundRelation.targetElement.isVO) {
                    return "@ElementCollection";
                } else {
                    return "@OneToMany(mappedBy = \"" + aggName + "\", cascade = CascadeType.ALL, orphanRemoval = true)";
                }
            }else{
                if(className.includes("List<")){
                    return "@ElementCollection";
                }else{
                    return "@Enumerated(EnumType.STRING)";
                }
            }
        } else {
            if (isVO === true) {
                if (isKey === true) {
                    return "@EmbeddedId";
                } else {
                    return "@Embedded";
                }
            } else if (relation) {
                const fields = relation.filter(field => field != null);
                
                const foundField = fields.find(field => 
                    field && field.name === name && field.targetElement);

                var aggName = foundField.sourceElement.name;
                aggName = aggName.charAt(0).toLowerCase() + aggName.slice(1);
                    
                if (foundField) {
                    if (className === foundField.targetElement.namePascalCase && 
                        foundField.targetElement._type.endsWith("enum")) {
                        return "@Enumerated(EnumType.STRING)";
                    } else if (foundField.targetElement.isVO) {
                        return "@Embedded";
                    } else {
                        return "@OneToOne(mappedBy = \"" + aggName + "\", cascade = CascadeType.ALL, orphanRemoval = true)";
                    }
                }
            }
        }
    } catch (e) {
        console.log(e);
    }
});

window.$HandleBars.registerHelper('checkExtends', function (relations, name) {
    try {
        if(typeof relations === "undefined" || name === "undefined"){
            return;
        } else {
            for(var i = 0; i < relations.length; i ++ ){
                if(relations[i] != null){
                    if(relations[i].sourceElement.name == name && relations[i].relationType.includes("Generalization")){
                        var text = "extends " + relations[i].targetElement.name
                        return text
                    }
                }
            }
        }
    } catch(e) {
        console.log(e)
    }
});

window.$HandleBars.registerHelper('setDiscriminator', function (relations, name) {
    try {
        if (typeof relations == "undefined") {
            return 
        } else {
            for (var i = 0; i < relations.length; i ++ ) {
                if (relations[i] != null) {
                    var text = ''
                    if (relations[i].targetElement != "undefined") {
                        if(relations[i].targetElement.name.toLowerCase() == name && relations[i].relationType.includes("Generalization")) {
                            text = '@DiscriminatorColumn(\n' + 
                                '    discriminatorType = DiscriminatorType.STRING,\n' +
                                '    name = "' + name + '_type",\n' +
                                '    columnDefinition = "CHAR(5)"\n' +
                                ')'
                            return text
                        }
                    } else {
                        if(relations[i].toName.toLowerCase() == name && relations[i].relationType.includes("Generalization")) {
                            text = '@DiscriminatorColumn(\n' + 
                                '    discriminatorType = DiscriminatorType.STRING,\n' +
                                '    name = "' + name + '_type",\n' +
                                '    columnDefinition = "CHAR(5)"\n' +
                                ')'
                            return text
                        }
                    }
                    if (relations[i].sourceElement != "undefined") {
                        if (relations[i].sourceElement.name.toLowerCase() == name && relations[i].relationType.includes("Generalization")) {
                            return '@DiscriminatorValue("' + name + '")'
                        }
                    } else {
                        if (relations[i].fromName.toLowerCase() == name && relations[i].relationType.includes("Generalization")) {
                            return '@DiscriminatorValue("' + name + '")'
                        }
                    }
                }
            }
        }
    } catch(e) {
        console.log(e)
    }
});

window.$HandleBars.registerHelper('setOperations', function (commands, name, options) {
    try {
        if(commands == "undefined") {
            return options.fn(this);
        }
        var isCmd = false;
        for (var i = 0; i < commands.length; i ++ ) {
            if(commands[i] != null) {
                if (commands[i].name == name && commands[i].isRestRepository != true) {
                    isCmd = true
                }
            }
        }
        if(isCmd) {
            return options.inverse(this);
        } else {
            return options.fn(this);
        }
    } catch(e) {
        console.log(e)
    }
});

window.$HandleBars.registerHelper('correlationGetSet', function (setter, getter,options) {
    let obj = {
        source: null,
        target: null
    };
   
    if(setter && setter.fieldDescriptors){
        obj.source = setter.fieldDescriptors.find(x=> x.isCorrelationKey);
    }
    if(getter && getter.fieldDescriptors){
        obj.target = getter.fieldDescriptors.find(x => x.isCorrelationKey);
    }
    
    return options.fn(obj);
});


window.$HandleBars.registerHelper('has', function (members) {
    try {
        return (members.length > 0);
    } catch(e) {
        console.log(e)
    }
});


</function>
