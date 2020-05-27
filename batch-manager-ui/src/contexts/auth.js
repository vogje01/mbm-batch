import React, {createContext, useCallback, useContext, useEffect, useState} from 'react';
import themes from "devextreme/ui/themes";
import {errorMessage} from "../utils/message-util";

const AuthContext = createContext({});
const useAuth = () => useContext(AuthContext);

function AuthProvider(props) {
    const [user, setUser] = useState();
    const [loading, setLoading] = useState(true);
    const [timer, setTimer] = useState(0);
    const [error, setError] = useState(false);

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
                    setUser({
                        user: data.userDto.userId,
                        firstName: data.userDto.firstName,
                        lastName: data.userDto.lastName,
                        avatarUrl: data.userDto._links.avatar.href
                    });
                    themes.current(data.userDto.theme);
                    setTimer(setInterval(ping, 300000));
                }
                setLoading(false);
            })
            .catch(() => {
                errorMessage('Login error: userId ' + userId + ' not authorized');
                throw new Error("Login error: userId ' + userId + ' not authorized")
            });
    }, []);

    const ping = useCallback(() => {
        fetch(process.env.REACT_APP_API_URL + 'ping', {headers: {'Authorization': 'Bearer ' + localStorage.getItem('webToken')}})
            .then((response) => {
                if (response.status !== 200) {
                    clearInterval(timer);
                    logOut();
                }
            })
    }, []);

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
