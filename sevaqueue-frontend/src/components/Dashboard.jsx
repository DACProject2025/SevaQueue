import React from 'react';
import { useAuth } from '../context/AuthContext';
import AdminDashboard from './AdminDashboard';
import StaffDashboard from './StaffDashboard';
import CitizenDashboard from './CitizenDashboard';

const Dashboard = () => {
    const { user } = useAuth();

    if (!user) return <div>Loading...</div>;

    // Normalize role check (handle potential differences in case or prefix)
    const role = user.role ? user.role.toUpperCase() : '';

    if (role === 'ADMIN' || role === 'ROLE_ADMIN') {
        return <AdminDashboard />;
    } else if (role === 'STAFF' || role === 'ROLE_STAFF') {
        return <StaffDashboard />;
    } else {
        // Default to Citizen
        return <CitizenDashboard />;
    }
};

export default Dashboard;

