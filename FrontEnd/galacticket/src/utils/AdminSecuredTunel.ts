import React, { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';

const AdminProtection = ({ children }) => {
  const [loading, setLoading] = useState(true);
  const [isAuth, setIsAuth] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    const checkAuth = async () => {
      const role = localStorage.getItem('role');
      const isloggedin = localStorage.getItem('isloggedin') === 'true';

      setIsAuth(isloggedin);

      if (role && role === 'ADMIN') {
        setIsAdmin(true);
      } else {
        setIsAdmin(false);
      }

      setLoading(false);
    };

    checkAuth();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (!isAuth) {
    return <Navigate to="/login" />;
  }

  if (!isAdmin) {
    return <Navigate to="/access" />;
  }

  return children;
};

export default AdminProtection;