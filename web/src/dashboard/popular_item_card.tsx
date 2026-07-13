import { Badge, Card, Text } from "@mantine/core";

type Props = {
  name: string;
  location: string;
  type: "Lost" | "Found";
  icon: string;
};

function PopularItemCard({ name, location, type, icon }: Props) {
  return (
    <Card className="popular-card" radius="lg" padding="md">
      <div className="item-icon-box">{icon}</div>

      <Text fw={700} size="sm">
        {name}
      </Text>

      <Text size="xs" c="dimmed">
        {location}
      </Text>

      <Badge
        color={type === "Lost" ? "maroon" : "yellow"}
        variant="light"
        mt="xs"
      >
        {type}
      </Badge>
    </Card>
  );
}

export default PopularItemCard;