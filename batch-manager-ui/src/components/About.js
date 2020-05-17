import React from 'react';
import {Popup} from "devextreme-react/popup";
import {Button} from "devextreme-react";
import {Box, Item} from "devextreme-react/box";

class About extends React.Component {

    render() {
        return (
            <React.Fragment>
                <Popup
                    id={'aboutPopup'}
                    className={'popup'}
                    visible={true}
                    dragEnabled={true}
                    closeOnOutsideClick={true}
                    showTitle={true}
                    title={'About'}
                    height={'auto'}
                    width={'auto'}
                    minWidth={400}
                    minHeight={100}
                    maxWidth={1200}
                    maxHeight={1200}
                    resizeEnabled={true}
                    onHiding={this.props.closePopup}>
                    <Box direction="row"
                         width="100%">
                        <Item ratio={1}>
                            <div className={'content dx-fieldset'}>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>Name:</div>
                                    <div className={'dx-field-label'}>{process.env.REACT_APP_NAME}</div>
                                </div>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>Version:</div>
                                    <div className={'dx-field-label'}>{process.env.REACT_APP_VERSION}</div>
                                </div>
                            </div>
                        </Item>
                    </Box>
                    <Button
                        width={90}
                        text={'Close'}
                        type={'success'}
                        stylingMode={'contained'}
                        onClick={this.props.closePopup}
                        style={{float: 'right', margin: '5px 10px 10px 0'}}/>
                </Popup>
            </React.Fragment>
        );
    }
}

export default About;