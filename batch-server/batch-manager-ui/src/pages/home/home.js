import React from 'react';
import './home.scss';
import appInfo from '../../app-info';

export default () => (
    <React.Fragment>
        <h2 className={'content-block'}>Home</h2>
        <div className={'content-block'}>
            <div className={'dx-card responsive-paddings'}>
                <p>Welcome to <b>{appInfo.title}</b>!</p>
            </div>
        </div>
    </React.Fragment>
);
