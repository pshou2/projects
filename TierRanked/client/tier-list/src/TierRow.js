import TierHeading from "./TierHeading";
import { useState } from "react";
import { Droppable } from "react-beautiful-dnd";
import TestItem from "./TestItem";
import styled from "styled-components";

const TierRowContainer = styled.div`
  background-color: #1a1a1a;
  min-width: 100px;
  margin: 10px 0;
  display: grid;
  ${(props) =>
    props.row !== "row-tray" ? "grid-template-columns: 60px 1fr" : ""};
  align-items: center;
`;

const ItemsContainer = styled.div`
  background-color: ${(props) => (props.isDraggingOver ? "blue" : "#1a1a1a")};
  display: inline-flex;
  flex-wrap: wrap;
  min-height: 60px;
`;

function TierRow({ row, letter, color, items, itemMap }) {
  return (
    <TierRowContainer row={row}>
      {letter && color && <TierHeading letter={letter} color={color} />}
      <ItemsContainer>
        {items?.map((url, index) => (
          <TestItem item={url} itemMap={itemMap} index={index} key={index} />
        ))}
      </ItemsContainer>
    </TierRowContainer>
  );
}









export default TierRow;
