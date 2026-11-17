import json, pathlib, struct, zlib, random
root=pathlib.Path("src/main/resources")
mod="scaffolding_ultra"
parts={
"standard":("Stojak pionowy","Vertical standard",[[6,0,6,10,16,10],[3,2,3,13,3,13],[3,8,3,13,9,13],[3,14,3,13,15,13]],[" I "," I "," I "],4),
"ledger":("Rygiel poziomy","Horizontal ledger",[[0,7,6,16,9,10],[0,5,5,2,11,11],[14,5,5,16,11,11]],["III"],4),
"brace":("Stężenie ukośne","Diagonal brace",[[0,0,7,4,4,9],[3,3,7,7,7,9],[6,6,7,10,10,9],[9,9,7,13,13,9],[12,12,7,16,16,9]],["  I"," I ","I  "],4),
"deck":("Podest stalowy","Steel deck",[[0,0,0,16,3,16]],["III","III"],3),
"base_jack":("Podstawka regulowana","Base jack",[[2,0,2,14,2,14],[6,2,6,10,12,10],[4,5,4,12,7,12]],[" I "," N ","III"],2),
"ladder":("Drabina stalowa","Steel ladder",[[2,0,6,4,16,9],[12,0,6,14,16,9],[4,2,6,12,4,9],[4,6,6,12,8,9],[4,10,6,12,12,9],[4,14,6,12,16,9]],["I I","III","I I"],4),
"guardrail":("Poręcz","Guardrail",[[0,5,7,16,7,9],[0,13,7,16,15,9],[0,0,6,2,16,10],[14,0,6,16,16,10]],["III","I I","III"],4),
"stairs":("Schody stalowe","Steel stairs",[[0,0,0,16,4,4],[0,0,4,16,8,8],[0,0,8,16,12,12],[0,0,12,16,16,16]],["I  ","II ","III"],4)}
def write(path,data):
 p=root/path
 p.parent.mkdir(parents=True,exist_ok=True)
 p.write_text(json.dumps(data,ensure_ascii=False,indent=2),encoding="utf-8")
a=f"assets/{mod}"
d=f"data/{mod}"
pl={"itemGroup.scaffolding_ultra":"SCAFFOLDING ULTRA"}
en=dict(pl)
for name,(pol,eng,boxes,pattern,count) in parts.items():
 pl[f"block.{mod}.{name}"]=pol
 en[f"block.{mod}.{name}"]=eng
 elements=[{"from":b[:3],"to":b[3:],"faces":{face:{"texture":"#steel"} for face in ["north","south","east","west","up","down"]}} for b in boxes]
 write(f"{a}/models/block/{name}.json",{"parent":"minecraft:block/block","textures":{"particle":f"{mod}:block/steel","steel":f"{mod}:block/steel"},"elements":elements})
 write(f"{a}/models/item/{name}.json",{"parent":f"{mod}:block/{name}"})
 write(f"{a}/blockstates/{name}.json",{"variants":{f"facing={f}":{"model":f"{mod}:block/{name}","y":y} for f,y in [("north",0),("east",90),("south",180),("west",270)]}})
 key={"I":{"item":"minecraft:iron_ingot"}}
 if any("N" in row for row in pattern):key["N"]={"item":"minecraft:iron_nugget"}
 write(f"{d}/recipe/{name}.json",{"type":"minecraft:crafting_shaped","category":"building","pattern":pattern,"key":key,"result":{"id":f"{mod}:{name}","count":count}})
 write(f"{d}/loot_table/blocks/{name}.json",{"type":"minecraft:block","pools":[{"rolls":1,"entries":[{"type":"minecraft:item","name":f"{mod}:{name}"}],"conditions":[{"condition":"minecraft:survives_explosion"}]}]})
write(f"{a}/lang/pl_pl.json",pl)
write(f"{a}/lang/en_us.json",en)
write("data/minecraft/tags/block/mineable/pickaxe.json",{"replace":False,"values":[f"{mod}:{name}" for name in parts]})
write("data/minecraft/tags/block/needs_stone_tool.json",{"replace":False,"values":[f"{mod}:{name}" for name in parts]})
rng=random.Random(21)
raw=b""
for y in range(16):
 raw+=b"\x00"
 for x in range(16):
  v=max(0,min(255,170+rng.randint(-13,13)+(8 if x%5==0 else 0)))
  raw+=bytes((v,v+3,v+7,255))
def chunk(t,b):return struct.pack(">I",len(b))+t+b+struct.pack(">I",zlib.crc32(t+b)&0xffffffff)
p=root/f"{a}/textures/block/steel.png"
p.parent.mkdir(parents=True,exist_ok=True)
p.write_bytes(b"\x89PNG\r\n\x1a\n"+chunk(b"IHDR",struct.pack(">IIBBBBB",16,16,8,6,0,0,0))+chunk(b"IDAT",zlib.compress(raw))+chunk(b"IEND",b""))
print("Generated 8 block models, recipes, loot tables and steel texture")
