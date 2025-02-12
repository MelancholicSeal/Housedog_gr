<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';
import {useApplicationStore} from "@/stores/application.js";
const backendEnvVar = import.meta.env.VITE_BACKEND;

const { userData } = useApplicationStore();


const urlRef =  ref(backendEnvVar +'/api/rent/requestsofuser/' + userData.id);
const authRef = ref(true);
const data = ref(null);
console.log(urlRef);
const { loading, performRequest: GetData } = useRemoteData(urlRef, authRef, data);

const route = useRoute();

const userIdRef = ref(null);

onMounted(() => {
  GetData();
  userIdRef.value = route.params.id;
  console.log(data);
});
</script>

<template>
  <div>
    <table class="table">
      <thead>
      <tr>
        <th colspan="6" class="text-center fs-4">Rents</th>
      </tr>
      <tr>
        <th>Property Address</th>
        <th>Property City</th>
        <th>Property Type</th>
        <th>Property Owner</th>
        <th>Status</th>
      </tr>
      </thead>
      <tbody v-if="data">
      <tr v-for="row in data" :key="row.property.id">
        <td>{{ row.property.address }}</td>
        <td>{{ row.property.city }}</td>
        <td>{{ row.property.type }}</td>
        <td>{{ row.property.owner.username }}</td>
        <td v-if="row.rented === 'true'">Accepted</td>
        <td v-else>Pending</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>
