import React from "react";
import Toolbar, {Item} from 'devextreme-react/toolbar';
import history from "./History";
import About from "./About";

function renderLabel() {
    return <div className="toolbar-label"><h1><b>{process.env.REACT_APP_NAME}</b></h1></div>;
}

function renderIcon() {
    return <img src={process.env.REACT_APP_LOGO} alt={'momentum logo'} height={'40px'}/>;
}

class TopToolbar extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showAbout: false
        };
        this.logout = this.logout.bind(this);
        this.toggleAbout = this.toggleAbout.bind(this);
    }

    logout() {
        localStorage.clear();
        this.setState({});
        history.push('/');
    }

    toggleAbout() {
        this.setState({
            showAbout: !this.state.showAbout
        });
    }

    render() {
        return (
            <React.Fragment>
                <Toolbar style={{margin: '10px 0px 22px 5px'}}>
                    <Item location="before" widget="dxIcon" render={renderIcon}/>
                    <Item location="center"
                          locateInMenu="never"
                          render={renderLabel}/>
                    <Item locateInMenu="always"
                          widget="dxButton"
                          visible={localStorage.getItem('authenticated') === 'true'}
                          options={{icon: 'material-icons-outlined ic-info', text: 'About', onClick: this.toggleAbout}}/>
                    <Item locateInMenu="always"
                          widget="dxButton"
                          visible={localStorage.getItem('authenticated') === 'true'}
                          options={{icon: 'material-icons-outlined ic-exit', text: 'Logout', onClick: this.logout}}/>
                </Toolbar>
                {
                    this.state.showAbout ? <About closePopup={this.toggleAbout.bind(this)}/> : null
                }

            </React.Fragment>
        );
    }
}

export default TopToolbar;