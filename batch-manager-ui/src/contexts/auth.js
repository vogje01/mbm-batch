import React, {createContext, useCallback, useContext, useEffect, useState} from 'react';
import themes from "devextreme/ui/themes";
import {errorMessage} from "../utils/message-util";

const AuthContext = createContext({});
const useAuth = () => useContext(AuthContext);

function AuthProvider(props) {
    const [user, setUser] = useState();
    const [loading, setLoading] = useState(true);
    const [setError] = useState(false);

    const logIn = useCallback(async (userId, password) => {
        // Send login request
        let authRequest = {userId: userId, password: password, orgUnit: 'EXT'}
        let basicAuthentication = 'Basic ' + new Buffer(userId + ':' + password + ':EXT').toString('base64');
        fetch(process.env.REACT_APP_API_URL + 'authenticate', {
            headers: {'Content-Type': 'application/hal+json', 'Authorization': basicAuthentication},
            method: 'POST', body: JSON.stringify(authRequest)
        })
            .then((response) => {
                if (response.status === 200) {
                    return response.json();
                }
                errorMessage('Login error: userId ' + userId + ' not authorized');
                setError(true);
                setLoading(false);
            })
            .then((data) => {
                if (data !== undefined) {
                    localStorage.setItem('webToken', data.token);
                    localStorage.setItem('user', JSON.stringify(data.userDto));
                    setUser({user: userId, avatarUrl: data.userDto._links.avatar.href});
                    themes.current(data.userDto.theme);
                }
                setLoading(false);
            })
            .catch(() => {
                errorMessage('Login error: userId ' + userId + ' not authorized');
                setLoading(false);
            });
    }, [setError]);

    const logOut = useCallback(() => {
        // Clear user data
        setUser();
    }, []);

    useEffect(() => {
        setLoading(false);
    }, []);

    return (
        <AuthContext.Provider value={{user, logIn, logOut, loading}} {...props} />
    );
}

export {AuthProvider, useAuth}
