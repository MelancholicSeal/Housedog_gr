<script setup>
import { ref } from 'vue';
import { useRemoteData } from '@/composables/useRemoteData.js';
import {useRoute, useRouter} from 'vue-router';
import { useApplicationStore } from '@/stores/application.js';
import {routes} from "vue-router/auto-routes";
import DatePicker from 'primevue/datepicker';

const backendEnvVar = import.meta.env.VITE_BACKEND;
const route = useRoute();
const router = useRouter();
const applicationStore = useApplicationStore();
const userId = applicationStore.userData?.id;
const propertyId = route.params.id;

const formDataRef = ref({
    id: '',
    start_date: '',
    end_date: '',
    user:{
      id: userId
    },
    property:{
      id: propertyId
    }
});
const urlRef = ref(backendEnvVar + '/api/rent');
const authRef = ref(true);
const methodRef = ref('POST');
const data = ref(null);
const { loading, performRequest } = useRemoteData(urlRef, authRef,data, methodRef, formDataRef);

const onSubmit = () => {
    performRequest()
        .then((data) => {
            router.push({ name: 'home'});

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
                        <RouterLink class="small" :to="{ name: 'property' }"
                            >Back to Property</RouterLink
                        >
                        <h1 class="fs-3">New Rent</h1>
                    </div>
                    <div>
                        <div class="mb-2">
                            <label for="start_date">Start Date</label>
                            <DatePicker v-model="formDataRef.start_date" dateFormat="dd/mm/yy" showButtonBar :invalid="!formDataRef.start_date"/>
                        </div>
                      <div class="mb-2">
                        <label for="end_date">End Date</label>
                        <DatePicker v-model="formDataRef.end_date" dateFormat="dd/mm/yy" :invalid="!formDataRef.end_date"/>
                      </div>
                        <div class="">
                            <button class="btn btn-primary" @click="onSubmit" type="button">
                                Request new Rent
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
