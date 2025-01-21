<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND;
const route = useRoute();

const courseIdRef = ref(null);
const urlRef = computed(() => {
  return backendEnvVar + '/api/property/' + propertyIdRef.value + '/users';
});
const authRef = ref(true);
const { data, loading, performRequest } = useRemoteData(urlRef, authRef);

onMounted(() => {
  propertyIdRef.value = route.params.id;
  performRequest();
});
</script>

<template>
  <div>
    <table class="table">
      <thead>
      <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th>Phone Number</th>
      </tr>
      </thead>
      <tbody v-if="data">
      <tr v-for="user in data" :key="user.id">
        <td>{{ user.firstName }}</td>
        <td>{{ user.lastName }}</td>
        <td>{{ user.email }}</td>
        <td>{{ user.phoneNumber}}</td>
        <td>
          <RouterLink :to="{ name: 'user-details', params: { id: user.id } }"
          >Display</RouterLink
          >
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>
