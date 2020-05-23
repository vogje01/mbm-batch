import React, {createContext, useCallback, useContext, useEffect, useState} from 'react';
import {loginRequest} from "../utils/server-connection";

const AuthContext = createContext({});
const useAuth = () => useContext(AuthContext);
const defaultUser = {
  userId: 'admin',
  avatarUrl: 'https://js.devexpress.com/Demos/WidgetsGallery/JSDemos/images/employees/06.png'
}

function AuthProvider(props) {
  const [user, setUser] = useState();
  const [loading, setLoading] = useState(true);

  const logIn = useCallback(async (userId, password) => {

    // Send login request
    let authRequest = {userId: userId, password: password, orgUnit: 'EXT'}
    let basicAuthentication = new Buffer(userId + ':' + password + ':EXT').toString('base64');

    loginRequest(basicAuthentication, authRequest).then((data) => {
      localStorage.setItem('authenticated', 'true');
      localStorage.setItem('webToken', data.token);
      localStorage.setItem('user', JSON.stringify(data.userDto));
      setUser({userId, avatarUrl: 'material-icons-outlined ic-dashboard'});
    });

    /*setUser({
      userId,
      avatarUrl: defaultUser.avatarUrl
    });*/
  }, []);

  const logOut = useCallback(() => {
    // Clear user data

    setUser();
  }, []);

  useEffect(() => {
    // Retrieve and save user data on initial load

    setUser(defaultUser);
    setLoading(false);
  }, []);

  return (
      <AuthContext.Provider value={{user, logIn, logOut, loading}} {...props} />
  );
}

export {AuthProvider, useAuth}
