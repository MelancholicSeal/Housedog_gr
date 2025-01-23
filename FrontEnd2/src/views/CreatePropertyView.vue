<script setup>
import { ref } from 'vue';
import { useRemoteData } from '@/composables/useRemoteData.js';
import { useRouter } from 'vue-router';
const backendEnvVar = import.meta.env.VITE_BACKEND;

const router = useRouter();

// @EXERCISE : Add teacher (dropdown - load data [background], pre-process data, show data in dropdown)
const formDataRef = ref({
    address: '',
    city: ''
});
const urlRef = ref(backendEnvVar + '/api/property');
const authRef = ref(true);
const methodRef = ref('POST');

const { data, performRequest } = useRemoteData(urlRef, authRef, methodRef, formDataRef);

const onSubmit = () => {
    performRequest()
        .then((data) => {
            router.push({ name: 'property' });
        })
        .catch((ignored) => {
            // TODO Handle error.
        });
};
</script>

<template>
    <div class="bg-body-tertiary">
        <div class="container">
            <div class="row py-4 px-3">
                <div class="col-12">
                    <div class="mb-4">
                        <RouterLink class="small" :to="{ name: 'properties' }"
                            >Back to Properties</RouterLink
                        >
                        <h1 class="fs-3">New Property</h1>
                    </div>
                    <div>
                        <div class="mb-2">
                            <label for="address">Address</label>
                            <input
                                class="form-control"
                                id="address"
                                v-model="formDataRef.address"
                                type="text"
                            />
                        </div>
                        <div class="mb-2">
                            <label for="city">City</label>
                            <input
                                class="form-control"
                                id="city"
                                v-model="formDataRef.city"
                                type="text"
                            />
                        </div>
                        <div class="">
                            <button class="btn btn-primary" @click="onSubmit" type="button">
                                Create new property
                            </button>
                        </div>
                    </div>
                    <pre>{{ formDataRef }}</pre>
                    <pre>{{ data }}</pre>
                </div>
            </div>
        </div>
    </div>
</template>
