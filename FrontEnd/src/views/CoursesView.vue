<script setup>
import { onMounted, ref } from 'vue';
import { useRemoteData } from '@/composables/useRemoteData.js';
const backendEnvVar = import.meta.env.VITE_BACKEND;

// @EXERCISE : Create dynamic pagination mechanism page=PAGE size=SIZE
// + create a generic reusable component.
const urlRef = ref(backendEnvVar + '/api/property?page=0&size=100');
const authRef = ref(true);
const { data, loading, performRequest } = useRemoteData(urlRef, authRef);

onMounted(() => {
    performRequest();
});
</script>

<template>
    <div class="bg-body-tertiary">
        <div class="container">
            <div class="row py-4 px-3">
                <div class="col-12">
                    <div class="mb-4">
                        <RouterLink class="small" :to="{ name: 'home' }">Back to Home</RouterLink>
                        <h1 class="fs-3">Courses</h1>
                        <RouterLink class="small" :to="{ name: 'property-new' }"
                            >Create Course</RouterLink
                        >
                    </div>
                    <div>
                        <table class="table">
                            <thead>
                                <tr>
                                    <!-- <th>Property ID</th> -->
                                    <th>Property Address</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody v-if="data">
                                <tr v-for="property in data">
                                    <td>{{ property.address }}</td>
                                    <td>
                                        <!-- TODO property.id -->
                                        <RouterLink
                                            :to="{
                                                name: 'property-details',
                                                params: { id: property.id }
                                            }"
                                        >
                                            Display
                                        </RouterLink>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <pre>{{ data }}</pre>
                </div>
            </div>
        </div>
    </div>
</template>
