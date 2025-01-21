<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND;

const router = useRouter();
const route = useRoute();

const userIdRef = ref(null);
const urlRef = computed(() => {
    return '/api/user/' + userIdRef.value + '/properties';
});
const authRef = ref(true);
const { data, loading, performRequest } = useRemoteData(urlRef, authRef);

onMounted(() => {
    userIdRef.value = route.params.id;
    performRequest();
});
</script>

<template>
    <div>
        <table class="table">
            <thead>
                <tr>
                    <!-- <th>Property ID</th> -->
                    <th>Property Title</th>
                </tr>
            </thead>
            <tbody v-if="loading">
                <tr>
                    <td colspan="1">Loading...</td>
                </tr>
            </tbody>
            <tbody v-if="data">
                <tr v-for="property in data._embedded.properties">
                    <!-- <th>{{ property.id }}</th> -->
                    <td>{{ property.title }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
