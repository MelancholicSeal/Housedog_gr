<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND;

const router = useRouter();
const route = useRoute();

const propertyIdRef = ref(null);
const urlRef = computed(() => {
    return backendEnvVar + '/api/property/' + propertyIdRef.value;
});
const authRef = ref(true);
const data = ref(null);
const {loading, performRequest: PropertyData } = useRemoteData(urlRef, authRef,data);

onMounted(() => {
    propertyIdRef.value = route.params.id;
    PropertyData();
});
</script>
<template>
    <div>
        <table class="table">
            <tbody v-if="data">
                <tr>
                    <th>ID</th>
                    <td>{{ data.id }}</td>
                </tr>
                <tr>
                    <th>Address</th>
                    <td>{{ data.address }}</td>
                </tr>
                <tr>
                    <th>City</th>
                    <td>{{ data.city }}</td>
                </tr>
                <tr>
                  <th>Type</th>
                  <td>{{ data.type }}</td>
                </tr>
                <tr>
                  <th>Available</th>
                  <td>{{ data.available }}</td>
                </tr>
            </tbody>
        </table>
        <pre>{{ data }}</pre>
    </div>
</template>
