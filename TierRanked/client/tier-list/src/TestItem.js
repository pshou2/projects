import { Draggable } from "react-beautiful-dnd";
import styled from "styled-components";

const TestItemContainer = styled.div`
  background-color: #efefef;
  display: inline-block;
  overflow: hidden;
  ${(props) => !props.backgroundImage && "box-shadow: inset 0 0 10px #000; "};
  width: 60px;
  height: 60px;
`;

function TestItem({ item }) {
  return (
    <TestItemContainer backgroundImage={item}>
      {item && (
        <img
          src={item.replace(/"/g, "")}
          alt="Image"
          style={{ width: "100%", height: "100%" }}
        />
      )}
    </TestItemContainer>
  );
}

export default TestItem;
