forEach: Aggregate
fileName: {{namePascalCase}}.vue
---
<template>
    <div>
        {{#aggregateRoot.fieldDescriptors}}
        {{#if (isNotId nameCamelCase)}}
        {{#if (isPrimitive className)}}
        {{#if isList}}
        <v-divider class="border-opacity-50 my-divider my-2"></v-divider>
        <div variant="outlined" class="my-2">
            <{{getPrimitiveType className}}
                label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}"
                v-model="{{nameCamelCase}}Input"
                :editMode="editMode"
                @keydown.enter="add{{namePascalCase}}List"
            />
            <v-card v-if="value.{{nameCamelCase}}.length > 0" variant="outlined" class="pa-4 mt-2">
                <v-card-sub-title>
                    {{#ifNotNull displayName namePascalCase}}{{/ifNotNull}} List
                </v-card-sub-title>
                <li v-for="(name, index) in value.{{nameCamelCase}}" :key="index">
                    \{{ name }}
                </li>
            </v-card>
            <v-row class="ma-0 pa-0 mt-2">
                <v-spacer></v-spacer>
                <v-btn @click="add{{namePascalCase}}List">
                    {{namePascalCase}} 추가
                </v-btn>
            </v-row>
            
            <{{getEntityFromList className}}DetailGrid
                label="{{namePascalCase}}"
                offline
                v-model="value.{{nameCamelCase}}"
                :editMode="editMode"
                @change="change"
            />
        </div>
        <v-divider class="border-opacity-50 my-divider my-2"></v-divider>
        {{else}}
        <{{getPrimitiveType className}}
            label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}"
            v-model="value.{{nameCamelCase}}"
            :editMode="editMode"
        />
        {{/if}}
        {{else}}
        {{#if isList}}
        {{#if (getPrimitiveType className)}}
        <{{getPrimitiveType className}}
            offline
            label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}"
            v-model="value.{{nameCamelCase}}"
            :editMode="editMode"
            @change="change"
        />
        {{else}}
        <div variant="outlined" class="my-2">
            <{{namePascalCase}} v-model="{{nameCamelCase}}Input"
                offline
                label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}"
                :editMode="editMode"
                @change="change"
            />
            <v-row class="ma-0 pa-0 mt-2">
                <v-spacer></v-spacer>
                <v-btn @click="add{{namePascalCase}}List">{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}} Add</v-btn>
            </v-row>
            <v-card v-if="value.{{nameCamelCase}}.length > 0" variant="outlined" class="pa-4 mt-2">
                <v-card-sub-title>
                    {{#ifNotNull displayName namePascalCase}}{{/ifNotNull}} List
                </v-card-sub-title>
                <li v-for="(id, index) in value.{{nameCamelCase}}" :key="index">
                    \{{ id && id.{{#../boundedContext.aggregates}}{{#outgoingRelations}}{{#target}}{{#attached 'BoundedContext' this}}{{#aggregates}}{{#if (compareName name ../../../../../../aggregateRoot.fieldDescriptors)}}{{#getNameField aggregateRoot.fieldDescriptors}}{{/getNameField}}{{/if}}{{/aggregates}}{{/attached}}{{/target}}{{/outgoingRelations}}{{/../boundedContext.aggregates}} ? id.{{#../boundedContext.aggregates}}{{#outgoingRelations}}{{#target}}{{#attached 'BoundedContext' this}}{{#aggregates}}{{#getNameField aggregateRoot.fieldDescriptors}}{{/getNameField}}{{/aggregates}}{{/attached}}{{/target}}{{/outgoingRelations}}{{/../boundedContext.aggregates}} : '' }}
                </li>
            </v-card>
        </div>
        {{/if}}
        {{/if}}
        {{/if}}
        {{/if}}
        {{/aggregateRoot.fieldDescriptors}}
        {{#aggregateRoot.fieldDescriptors}}
        {{#unless isKey}}
        {{#if (isNotId nameCamelCase)}}
        {{#if isVO}}
        {{#checkVO className}}
        <{{className}}
            offline
            label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}"
            v-model="value.{{nameCamelCase}}"
            :editMode="editMode"
            @change="change"
        />
        {{/checkVO}}
        {{/if}}
        {{/if}}
        {{/unless}}
        {{#if isList}}
        {{else}}
        {{#if (isNotId nameCamelCase)}}
        {{#if (isPrimitive className)}}
        {{else}}
        {{#checkEntityMember className}}
        {{#if (getPrimitiveType className)}}
        <{{getPrimitiveType className}}
            offline
            label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}"
            v-model="value.{{nameCamelCase}}"
            :editMode="editMode"
            @change="change"
        />
        {{else}}
        <{{className}}
            offline
            label="{{#ifNotNull displayName namePascalCase}}{{/ifNotNull}}"
            v-model="value.{{nameCamelCase}}"
            :editMode="editMode"
            @change="change"
        />
        {{/if}}
        {{/checkEntityMember}}
        {{/if}}
        {{/if}}
        {{/if}}
        {{/aggregateRoot.fieldDescriptors}}
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: '{{namePascalCase}}',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "{{namePlural}}",
        {{#aggregateRoot.fieldDescriptors}}
        {{#if isList}}
        {{nameCamelCase}}Input: null,
        {{/if}}
        {{/aggregateRoot.fieldDescriptors}}
        value: {
            {{#aggregateRoot.fieldDescriptors}}
            {{#if isList}}
            {{nameCamelCase}}: [],
            {{/if}}
            {{/aggregateRoot.fieldDescriptors}}
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
        {{#aggregateRoot.fieldDescriptors}}
        {{#if isList}}
        add{{namePascalCase}}List() {
            if (this.{{nameCamelCase}}Input !== null && this.{{nameCamelCase}}Input !== '') {
                this.value.{{nameCamelCase}}.push(this.{{nameCamelCase}}Input);
                this.{{nameCamelCase}}Input = null; // null로 초기화
            }
        },
        {{/if}}
        {{/aggregateRoot.fieldDescriptors}}
    },
}
</script>

<function>
    var importList = []
    var componentList = []

    window.$HandleBars.registerHelper('getEntityFromList', function (className) {
        if(className.includes("List<") && className.includes(">")) {
            return className.replace("List<", "").replace(">", "");
        }
        return className;
    })
    window.$HandleBars.registerHelper('ifNotNull', function (displayName, name) {
        if(displayName){
	        return displayName;
        }else{
	        return name;
        }
    })
    window.$HandleBars.registerHelper('checkVO', function (className, options) {
        if(className.endsWith("Address") || className.endsWith("Photo") || className.endsWith("User") || className.endsWith("Email") 
                || className.endsWith("Payment") || className.endsWith("Money") || className.endsWith("Weather") || className.endsWith("Rating") ){
            return options.fn(this);
        }
    })
    window.$HandleBars.registerHelper('checkCommandPut', function (info, options) {
        if(info.endsWith('PUT')){
            return options.fn(this);
        } else {
            options.inverse(this);
        }
    })

    window.$HandleBars.registerHelper('checkCommandDelete', function (info, options) {
        if(info.endsWith('DELETE')){
            return options.fn(this);
        } else {
            options.inverse(this);
        }
    })

    window.$HandleBars.registerHelper('checkCommandPost', function (info, options) {
        if(info.endsWith('POST')){
            return options.fn(this);
        } else {
            options.inverse(this);
        }
    })

    window.$HandleBars.registerHelper('isNotId', function (className) {
        return (className != 'id')
    })

    window.$HandleBars.registerHelper('isPrimitive', function (className) {
        if(className.includes("String") || className.includes("Integer") || className.includes("Long") || className.includes("Double") || className.includes("Float")
            || className.includes("Boolean") || className.includes("Date")){
            return true;
        } else {
            return false;
        }
    })
    window.$HandleBars.registerHelper('isPrimitiveImport', function (className) {
        if(!importList.includes(className)){
            importList.push(className)
            if(className.includes("String") || className.includes("Integer") || className.includes("Long") || className.includes("Double") || className.includes("Float")
                || className.includes("Boolean") || className.includes("Date")){
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    })
    window.$HandleBars.registerHelper('isPrimitiveComponent', function (className) {
        if(!componentList.includes(className)){
            componentList.push(className)
            if(className.includes("String") || className.includes("Integer") || className.includes("Long") || className.includes("Double") || className.includes("Float")
                || className.includes("Boolean") || className.includes("Date")){
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    })

    window.$HandleBars.registerHelper('getPrimitiveType', function (className, options) {
        if(className.includes("String")) {
            if(this.isLob) {
                return "LargeObject";
            } else {
                return "String";
            }
        } else if(className.includes("Integer") || className.includes("Long") || className.includes("Double") || className.includes("Float") || className.includes("int") || className.includes("BigDecimal")) {
            if(this.isLob) {
                return "LargeObject";
            } else {
                return "Number";
            }
        } else if(className.includes("Boolean")) {
            return "Boolean";
        } else if(className.includes("Date")) {
            return "Date";
        }
    })

    window.$HandleBars.registerHelper('checkEntityMember', function (className, options) {
        if(!(className.endsWith("Address") || className.endsWith("Photo") || className.endsWith("User") || className.endsWith("Email") 
                || className.endsWith("Payment") || className.endsWith("Money") || className.endsWith("Weather") || className.endsWith("Rating")) && className.indexOf("java.")==-1 && className.indexOf("List")==-1) {
            return options.fn(this);
        } else {
            return options.inverse(this);
        }
    })

    window.$HandleBars.registerHelper('checkListOfEntityMember', function (className, options) {
        if(className.indexOf("List")==0){
            return options.fn(this);
        } else {
            return options.inverse(this);
        }
    })

    window.$HandleBars.registerHelper('getEntityClassNameOfList', function (listClassName) {
        var regex = /\<(.*?)\>/g;
        var match = regex.exec(listClassName);
        if (match) {
            return (match[1]);
        }
        return "NO-CLASS";
    })

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
    })

    window.$HandleBars.registerHelper('isSelectedSecurity', function (selectedSecurity) {
        try{
            if(!selectedSecurity)
                return false;

            if(selectedSecurity == 'isKeycloakSecurity'){
                return true;
            }

            return;
        } catch(e){
            console.log(e)
        }
    });

    window.$HandleBars.registerHelper('hasRestRepository', function (commands) {
        try{
            if(commands == "undefined") {
                return options.fn(this);
            }
            var hasRestRepository = false;
            for (var i = 0; i < commands.length; i ++ ) {
                if(commands[i] != null) {
                    if (commands[i].isRestRepository == true) {
                        hasRestRepository = true
                    }
                }
            }

            return hasRestRepository;
        } catch(e){
            console.log(e)
        }
    });

    window.$HandleBars.registerHelper('attachedActorName', function (actorName, options) {
        try {
            if(actorName) {
                return true;
            } else {
                return false;
            }
        } catch(e) {
            console.log(e)
        }
    })

    window.$HandleBars.registerHelper('checkParam', function (fieldDescriptors, options) {
        try {
            if(fieldDescriptors == "undefined") {
                return false;
            } else {
                if(fieldDescriptors.length > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch(e) {
            console.log(e)
        }
    });

    window.$HandleBars.registerHelper('compareName', function (aggName, fieldDescriptors) {
        for(var i = 0; i < fieldDescriptors.length; i ++ ) {
            if(fieldDescriptors[i].referenceClass && aggName == fieldDescriptors[i].referenceClass){
                return true;
            }
        } 
        return false;
    });
    
    window.$HandleBars.registerHelper('getNameField', function (fieldDescriptors) {
        for(var i = 0; i < fieldDescriptors.length; i ++ ){
            if(fieldDescriptors[i] && fieldDescriptors[i].isName == true){
                console.log('fieldDescriptors[i].nameCamelCase : ture', fieldDescriptors[i].nameCamelCase)
                return fieldDescriptors[i].nameCamelCase
            }else if(fieldDescriptors[i].isName == false && fieldDescriptors[i].isKey == false){
                console.log('fieldDescriptors[i].nameCamelCase : false', fieldDescriptors[i].nameCamelCase)
                return fieldDescriptors[i].nameCamelCase
            }
        }
    });

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
        // console.log("value = " + value)
        if(uncountable.indexOf(value.toLowerCase()) >= 0) {
            return this;
        }

        // check for irregular forms
        for(var word in irregular) {

            if(revert) {
                var pattern = new RegExp(irregular[word]+'$', 'i');
                var replace = word;
            } else { 
                var pattern = new RegExp(word+'$', 'i');
                var replace = irregular[word];
            }
            if(pattern.test(value)) {
                return value.replace(pattern, replace);
            }
        }

        if(revert) {
            var array = singular;
        } else {
            var array = plural;
        }

        // check for matches using regular expressions
        for(var reg in array) {

            var pattern = new RegExp(reg, 'i');

            if(pattern.test(value)) {
                return value.replace(pattern, array[reg]);
            }
        }

        return value;
      }

      return pluralize(className.toLowerCase())
    })

</function>