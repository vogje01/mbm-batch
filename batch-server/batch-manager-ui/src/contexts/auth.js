import React, {createContext, useCallback, useContext, useEffect, useState} from 'react';
import themes from "devextreme/ui/themes";
import {errorMessage} from "../utils/message-util";

const AuthContext = createContext({});
const useAuth = () => useContext(AuthContext);

function AuthProvider(props) {
    const [user, setUser] = useState();
    const [loading, setLoading] = useState(false);
    const [timer, setTimer] = useState(0);

    const logOut = useCallback(() => {
        // Clear user data
        setUser(null);
    }, []);

    const ping = useCallback(() => {
        fetch(process.env.REACT_APP_MANAGER_URL + 'ping', {headers: {'Authorization': 'Bearer ' + localStorage.getItem('webToken')}})
            .then((response) => {
                if (response.status !== 200) {
                    clearInterval(timer);
                    logOut();
                }
            })
    }, [logOut, timer]);

    const logIn = useCallback((userId, password) => {
        // Send login request
        let authRequest = {userId: userId, password: password, orgUnit: 'EXT'}
        let basicAuthentication = 'Basic ' + new Buffer(userId + ':' + password + ':EXT').toString('base64');
        fetch(process.env.REACT_APP_MANAGER_URL + 'authenticate', {
            headers: {'Content-Type': 'application/hal+json', 'Authorization': basicAuthentication},
            method: 'POST', body: JSON.stringify(authRequest)
        })
            .then((response) => {
                if (response.status === 200) {
                    return response.json();
                }
                return undefined;
            })
            .then((data) => {
                if (data !== undefined) {
                    localStorage.setItem('webToken', data.token);
                    localStorage.setItem('user', JSON.stringify(data.userDto));
                    localStorage.setItem('theme', data.userDto.theme);
                    localStorage.setItem('dateTimeFormat', data.userDto.dateTimeFormat);
                    localStorage.setItem('numberFormat', data.userDto.numberFormat);
                    setUser({
                        user: data.userDto.userId,
                        firstName: data.userDto.firstName,
                        lastName: data.userDto.lastName,
                        avatarUrl: data.userDto._links.avatar.href
                    });
                    themes.current(data.userDto.theme);
                    setTimer(setInterval(ping, 300000));
                } else {
                    errorMessage('Login error: userId ' + userId + ' not authorized');
                }
            });
    }, [ping]);

    useEffect(() => {
        setLoading(false);
    }, []);

    return (
        <AuthContext.Provider value={{user, logIn, logOut, loading}} {...props} />
    );
}

export {AuthProvider, useAuth}
