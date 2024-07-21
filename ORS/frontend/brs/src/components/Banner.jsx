/* src/components/Banner.jsx */
import React from 'react';
import './Banner.css';

const Banner = () => {
    return (
        <div className="banner">
            <div className="banner-content">
                <h1 className="banner-title">Welcome to Our Platform</h1>
                <p className="banner-subtitle">Discover the best solutions tailored just for you.</p>
                <a href="/get-started" className="banner-cta">Get Started</a>
            </div>
        </div>
    );
};

export default Banner;
