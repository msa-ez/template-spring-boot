<template>

    <v-data-table
        :headers="headers"
        :items="상품조회"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: '상품조회View',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
            ],
            상품조회 : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/상품조회'))

            temp.data._embedded.상품조회.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.상품조회 = temp.data._embedded.상품조회;
        },
        methods: {
        }
    }
</script>

