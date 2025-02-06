import { ref } from 'vue';
import { useApplicationStore } from '@/stores/application.js';

const store = useApplicationStore();

export function useRemoteData(urlRef, authRef, dataRef, methodRef = ref('GET'), bodyRef = ref(null)) {
    const error = ref(null);
    const loading = ref(false);

    const performRequest = () => {
        loading.value = true;
        const headers = {
            'Content-Type': 'application/json'
        };

        if (authRef.value === true) {
            headers['Authorization'] = 'Bearer ' + store.userData.accessToken;
        }
        const config = {
            method: methodRef.value,
            headers: headers
        };

        if (bodyRef.value !== null) {
            config.body = JSON.stringify(bodyRef.value);
        }

        return fetch(urlRef.value, config)
            .then((response) => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error('Failed to fetch');
            })
            .then((responseData) => {
                dataRef.value = responseData;
                return Promise.resolve(dataRef.value);// Assign response to the external `dataRef`
            })
            .catch((err) => {
                error.value = err;
            })
            .finally(() => {
                loading.value = false;
            });
    };

    return { error, loading, performRequest };

}


